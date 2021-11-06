package com.example.doctruyenapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Category {
    @ColumnInfo(name = "category_name")
    public String categoryName;

    @ColumnInfo(name = "image")
    public String image;

    public Category(String categoryName, String image) {
        this.categoryName = categoryName;
        this.image = image;
    }
}
