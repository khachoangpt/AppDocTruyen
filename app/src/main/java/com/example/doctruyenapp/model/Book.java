package com.example.doctruyenapp.model;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
public class Book implements Serializable {
    public Long id;
    public String title;
    public String audio;
    public String author;
    public String description;
    public Float rating;
    public String image;
    public Long categoryId;

    public Book() {
    }

    public Book(Long id, String title, String audio, String author, String description, Float rating, String image, Long categoryId) {
        this.id = id;
        this.title = title;
        this.audio = audio;
        this.author = author;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.categoryId = categoryId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }



}
