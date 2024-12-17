package com.myApp.feedbackapp.model;

public class Feedback {
    private Integer id;
    private String username;
    private String content;

    public Feedback(Integer id, String username, String content) {
        this.id = id;
        this.username = username;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
