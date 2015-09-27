package com.practice.wysiwyg.media;

import org.jsoup.nodes.Element;

import java.util.regex.Pattern;

/**
 * Created by Eric on 9/27/2015.
 */
public class Image implements Media {
    public final static String ATTRIBUTE_SOURCE = "src";
    public final static Pattern POSTED_IMAGE_PATTERN = Pattern.compile("postedimage/\\d+");
    private String source;
    private String content;

    public Image(String source) {
        this.source = source;
    }

    public Image(Element element) {
        source = element.attr(ATTRIBUTE_SOURCE);
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public boolean isPosted() {
        if (POSTED_IMAGE_PATTERN.matcher(source).matches()) {
            return true;
        }

        return false;
    }

    public void setSource(String source) {
        this.source = source;
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
