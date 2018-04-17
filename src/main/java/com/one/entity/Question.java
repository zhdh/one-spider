package com.one.entity;

/**
 * Question object
 * @author https://github.com/zhdh
 */
public class Question {

    private String title;
    private String abstractContent;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractContent() {
        return abstractContent;
    }

    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", abstractContent='" + abstractContent + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
