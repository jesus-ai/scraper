/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package me.duncte123.biblescraper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final String BASE_URL = "http://getbible.net/json?p=";
    /// <editor-fold desc="PASSAGES">
    private static final String[] PASSAGES = {
            "Genesis",
            "Exodus",
            "Leviticus",
            "Numbers",
            "Deuteronomy",
            "Joshua",
            "Judges",
            "Ruth",
            "1 Samuel",
            "2 Samuel",
            "1 Kings",
            "2 Kings",
            "1 Chronicles",
            "2 Chronicles",
            "Ezra",
            "Nehemiah",
            "Esther",
            "Job",
            "Psalm",
            "Proverbs",
            "Ecclesiastes",
            "Song of Solomon",
            "Isaiah",
            "Jeremiah",
            "Lamentations",
            "Ezekiel",
            "Daniel",
            "Hosea",
            "Joel",
            "Amos",
            "Obadiah",
            "Jonah",
            "Micah",
            "Nahum",
            "Habakkuk",
            "Zephaniah",
            "Haggai",
            "Zechariah",
            "Malachi",
            "Matthew",
            "Mark",
            "Luke",
            "John",
            "Acts",
            "Romans",
            "1 Corinthians",
            "2 Corinthians",
            "Galatians",
            "Ephesians",
            "Philippians",
            "Colossians",
            "1 Thessalonians",
            "2 Thessalonians",
            "1 Timothy",
            "2 Timothy",
            "Titus",
            "Philemon",
            "Hebrews",
            "James",
            "1 Peter",
            "2 Peter",
            "1 John",
            "2 John",
            "3 John",
            "Jude",
            "Revelation",
    };
    /// </editor-fold>

    private App() throws Exception {
        File output = new File("bible.txt");

        createFileAndDeleteIfExists(output);

        try (FileWriter fw = new FileWriter(output)) {
            try (BufferedWriter writer = new BufferedWriter(fw)) {
                for (int index = 0; index < PASSAGES.length; index++) {
                    System.out.println(PASSAGES[index]);
                    JSONObject json = loadPage(index);

                    if (json == null) {
                        continue;
                    }

                    String bookName = json.getString("book_name");
                    JSONObject books = json.getJSONObject("book");
                    List<String> bookNames = books.names()
                            .toList()
                            .stream()
                            .map(String::valueOf)
                            .map(Integer::valueOf)
                            .sorted()
                            .map(String::valueOf)
                            .collect(Collectors.toList());

                    for (String bookNr : bookNames) {
//                    String bookNr = (String) item;
                        System.out.println(bookNr);
                        JSONObject book = books.getJSONObject(bookNr).getJSONObject("chapter");
                        writer.write(
                                parseBook(bookName, bookNr, book)
                        );
                    }
                }

            }
        }
    }

    private String parseBook(String bookName, String bookNr, JSONObject book) {
        StringBuilder builder = new StringBuilder();
        List<String> verseNumbers = book.names()
                .toList()
                .stream()
                .map(String::valueOf)
                .map(Integer::valueOf)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.toList());

        System.out.println(verseNumbers);

        System.out.println(book);

        for (String verseNum : verseNumbers) {
//            String verseNum = (String) item;
            System.out.println(verseNum);
            String verse = book.getJSONObject(verseNum).getString("verse").replaceAll("\r\n", "\n");

            builder.append(bookName)
                    .append(' ')
                    .append(bookNr)
                    .append(':')
                    .append(verseNum)
                    .append(' ')
                    .append(verse);
        }

        return builder.toString();
    }

    private void createFileAndDeleteIfExists(File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }

        file.createNewFile();
    }

    private JSONObject parseJSON(String in) {
        if (in == null || in.equalsIgnoreCase("NULL")) {
            return null;
        }

        return new JSONObject(
                new JSONTokener(
                        in.substring(1, in.length() - 2)
                )
        );
    }

    private JSONObject loadPage(int index) {
        Request request = new Request.Builder()
                .url(BASE_URL + PASSAGES[index])
                .get()
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            //noinspection ConstantConditions
            return parseJSON(response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        new App();
    }
}
