package com.blog.entity;

/**
 * @author Jennyism
 * @date 23/5/2020 下午11:21
 */
public class UeditorEntity {
    private  String state;
    private  String url;
    private  String title;
    private  String original;

    public UeditorEntity() {
        super();
    }

    public UeditorEntity(String state, String url, String title, String original) {
        this.state = state;
        this.url = url;
        this.title = title;
        this.original = original;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
