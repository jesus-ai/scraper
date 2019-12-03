package me.duncte123.biblescraper.scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class PhrasesOrgScraper extends BaseScraper<Document> {
    public PhrasesOrgScraper(String url, String fileName) {
        super(url, fileName, Jsoup::parse);
    }

    @Override
    protected List<String> getUsableText() {
        Document doc = this.getParsed();
        final List<String> items = doc.select("p.phrase-list")
                .stream()
                .map(Element::text)
                .collect(Collectors.toList());

        System.out.println(items);

        return items;
    }
}