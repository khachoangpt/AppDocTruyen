package com.example.doctruyenapp.model;

import androidx.room.Entity;

@Entity
public class Category {

    public String categoryName;
    public String image;

    public Category(String categoryName, String image) {
        this.categoryName = categoryName;
        this.image = image;
    }
}
