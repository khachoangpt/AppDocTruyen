package com.example.doctruyenapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Category {
    @ColumnInfo(name = "category_name")
    public String categoryName;

    @ColumnInfo(name = "image")
    public int image;

    public Category(String categoryName, int image) {
        this.categoryName = categoryName;
        this.image = image;
    }
}
