package me.duncte123.biblescraper.scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class SaidWhatScraper extends BaseScraper<Document> {

    public SaidWhatScraper(String url, String fileName) {
        super(url, fileName, Jsoup::parse);
    }

    @Override
    protected List<String> getUsableText() {
        final String[] items = this.getParsed().select("div#centercontent").html().split("\n");
        List<String> output = new ArrayList<>();

        System.out.println(items.length);

        for (String item : items) {
            if (item.startsWith("<br>")) {
                final String parsed = item.replaceAll("<br>", "").trim();

                if (!parsed.isBlank()) {
                    output.add(parsed);
                }
            }
        }

        System.out.println(output);

        return output;
    }
}
