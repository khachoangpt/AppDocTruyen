package com.example.doctruyenapp.model;

import androidx.room.Entity;

@Entity
public class Comment {

    private Long id;
    private Long bookId;
    private String name;
    private String content;
    private String createAt;

    public Comment(Long bookId, String content) {
        this.bookId = bookId;
        this.content = content;
    }

    public Comment(Long id, Long bookId, String name, String content, String createAt) {
        this.id = id;
        this.bookId = bookId;
        this.name = name;
        this.content = content;
        this.createAt = createAt;
    }

    public Comment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
