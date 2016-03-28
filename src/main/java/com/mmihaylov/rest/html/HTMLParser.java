package com.mmihaylov.rest.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser {

    static final String NOVINI_BG_EXPRESSION = "div.resizeable_text.fs_2";
    static final String SPAN_TOOLTIP = "i_tooltip";

    public String parseNews(String htmlPage) {
        Document document = Jsoup.parse(htmlPage);
        Elements elements = document.select(NOVINI_BG_EXPRESSION);
        StringBuilder stringBuilder = new StringBuilder();
        for(Element element : elements) {
            if(!element.hasText() || element.hasClass(SPAN_TOOLTIP)) {
                continue;
            }
            stringBuilder.append(element.text());
        }
        return stringBuilder.toString();
    }
}
