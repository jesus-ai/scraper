package me.duncte123.biblescraper.scrapers;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

public abstract class BaseScraper<T> {
    protected static final OkHttpClient CLIENT = new OkHttpClient();
    private final String url;
    private final String fileName;
    private final Function<String, T> converter;
    private T parsed;

    /**
     * The constructor for our base scraper
     *
     * @param url
     *         the url of the weboage to scraper
     * @param fileName
     *         the name of the file to store the contents in
     * @param converter
     *         the converter that converts the webpage into a format that we can parse into a webpage
     */
    public BaseScraper(String url, String fileName, Function<String, T> converter) {
        this.url = url;
        this.fileName = fileName;
        this.converter = converter;

        getAndParse();
        writeToFile();
    }

    /**
     * Returns a list of usable text that we can insert into the file
     *
     * @return A list of usable text for our text file
     */
    protected abstract List<String> getUsableText();

    /**
     * Returns a parsed format that we can handle to create the usable text
     *
     * @return The parsed format that we can use to create the usable text
     */
    protected T getParsed() {
        return this.parsed;
    }

    /**
     * Writes the usable text to a file
     */
    private void writeToFile() {
        try {
            File output = new File(this.fileName);

            if (output.exists()) {
                output.delete();
            }

            output.createNewFile();

            try (FileWriter fw = new FileWriter(output, StandardCharsets.UTF_8)) {
                try (BufferedWriter writer = new BufferedWriter(fw)) {

                    this.getUsableText().forEach((s) -> {
                        try {
                            writer.write(s);
                            writer.write("\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Downloads the content of a webpage and parses it with our converter
     */
    private void getAndParse() {
        Request request = new Request.Builder()
                .url(this.url)
                .get()
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            //noinspection ConstantConditions
            this.parsed = this.converter.apply(response.body().string());
            // Close the response when we are done
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
