/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package me.duncte123.biblescraper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        int passageIndex = 0;

        createFileIfNotExists(output);

        try (FileWriter fw = new FileWriter(output)) {
            try (BufferedWriter writer = new BufferedWriter(fw)) {
                // loop
                JSONObject json = loadPage(passageIndex);

                if (json == null) {
                    return;
                }

                String bookName = json.getString("book_name");
                JSONObject book = json.getJSONObject("book");
                JSONArray bookNames = book.names();
                int bookNameCount = bookNames.length();

            }
        }
    }

    private void createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private JSONObject parseJSON(String in) {
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

    private String parseBook(String bookName, JSONObject book) {


        return null;
    }

    public static void main(String[] args) throws Exception {
        new App();
    }
}
