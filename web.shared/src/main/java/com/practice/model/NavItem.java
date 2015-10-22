package com.practice.model;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/22/15
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavItem {
    private String caption;
    private String uri;


    public NavItem(String caption, String uri) {
        this.caption = caption;
        this.uri = uri;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
