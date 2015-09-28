package com.practice.wysiwyg.media;

import org.jsoup.nodes.Element;

import java.util.regex.Pattern;

/**
 * Created by Eric on 9/27/2015.
 */
public class Image implements Media {
    public final static String ATTRIBUTE_SOURCE = "src";

    private Element element;
    private String content;

    public Image(Element element) {
        this.element = element;
    }

    @Override
    public String getSource() {
        return element.attr(ATTRIBUTE_SOURCE);
    }

    public void setSource(String source) {
        element.attr(ATTRIBUTE_SOURCE, source);
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }
}
