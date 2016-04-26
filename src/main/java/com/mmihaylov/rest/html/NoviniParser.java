package com.mmihaylov.rest.html;

import com.mmihaylov.rest.resources.model.NewsEntity;
import com.mmihaylov.rest.utils.CommonUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoviniParser implements HtmlPageParser<NewsEntity> {

    static final String TITLE_EXPRESSION = "#news_open_content > h1";
    static final String CONTENT_EXPRESSION = "div.resizeable_text.fs_2";
    static final String SPAN_TOOLTIP = "i_tooltip";
    static final String PUBLICATION_DATE_EXPRESSION = "#news_open_content div.news_date";
    static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy | HH:mm");

    public NewsEntity parse(String htmlPage) {
        Document document = Jsoup.parse(htmlPage);
        NewsEntity newsEntity = new NewsEntity();
        String title = getTitle(document);
        newsEntity.setTitle(title);
        String content = getContent(document);
        newsEntity.setContent(content);
        Date publicationDate = getPublicationDate(document);
        newsEntity.setPublishedDate(publicationDate);
        return newsEntity;
    }

    private Date getPublicationDate(Document document) {
        Elements elements = document.select(PUBLICATION_DATE_EXPRESSION);
        if(CommonUtils.isNullOrEmpty(elements)) {
            throw new IllegalStateException("Html node with date is not found.");
        }
        Element element = elements.get(0);
        String text = element.text();
        try {
            Date publicationDate = dateFormat.parse(text);
            return publicationDate;
        } catch(ParseException pe) {
            throw new IllegalStateException("Fail to parse date from text: " + text);
        }
    }

    private String getTitle(Document document) {
        Elements elements = document.select(TITLE_EXPRESSION);
        StringBuilder stringBuilder = new StringBuilder();
        for(Element element : elements) {
            stringBuilder.append(element.text());
        }
        return stringBuilder.toString();
    }

    private String getContent(Document document) {
        Elements elements = document.select(CONTENT_EXPRESSION);
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