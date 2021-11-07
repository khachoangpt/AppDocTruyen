package com.example.doctruyenapp.model;

import androidx.room.Entity;

import java.io.Serializable;

@Entity
public class BookCategory implements Serializable {
    public Long id;
    private String name;
    private String image;

    public BookCategory() {
    }

    public BookCategory(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
