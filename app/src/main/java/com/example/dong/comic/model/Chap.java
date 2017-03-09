package com.example.dong.comic.model;

/**
 * Created by DONG on 07-Mar-17.
 */

public class Chap {
    private String title;
    private String href;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
