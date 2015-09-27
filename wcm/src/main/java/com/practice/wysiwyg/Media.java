package com.practice.wysiwyg;

/**
 * Created by Eric on 9/27/2015.
 */
public interface Media {
    String getSource();

    boolean isPosted();

    String getContent();

    void setContent(String content);
}
