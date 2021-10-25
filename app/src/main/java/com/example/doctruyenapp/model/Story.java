package com.example.doctruyenapp.model;

public class Story {
    private int id;
    private String title;
    private String content;
    private String image;
    private int id_acc;

    public Story(String title, String content, String image, int id_acc) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.id_acc = id_acc;
    }
    public Story(int id, String title, String content, String image, int id_acc) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.id_acc = id_acc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_acc() {
        return id_acc;
    }

    public void setId_acc(int id_acc) {
        this.id_acc = id_acc;
    }
}
