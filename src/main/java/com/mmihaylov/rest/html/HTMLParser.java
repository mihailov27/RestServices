package com.mmihaylov.rest.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {

    static final String NOVINI_BG_EXPRESSION = "div.resizeable_text fs_2 ";

    public String parseNews(String htmlPage) {
        Document document = Jsoup.parse(htmlPage);
        Elements elements = document.select(NOVINI_BG_EXPRESSION);
        StringBuilder stringBuilder = new StringBuilder();
        for(Element element : elements) {
            stringBuilder.append(element.text());
        }
        return stringBuilder.toString();
    }
}
