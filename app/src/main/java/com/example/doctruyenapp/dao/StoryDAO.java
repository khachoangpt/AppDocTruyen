package com.example.doctruyenapp.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.Story;

import java.util.List;

@Dao
public interface StoryDAO {
    //Get 3 newest story
    @Query("SELECT * FROM story ORDER BY id DESC LIMIT 3")
    List<Story> getThreeNewestStory();

    //Get all stories
    @Query("SELECT * FROM story")
    List<Story> getAllStory();

    //Get story by id
    @Query("SELECT * FROM story WHERE id = :id")
    Story getStoryById(int id);

    //Add new story into db
    @Insert
    void addStory(Story... story);

    //Delete a story
    @Delete
    void delete(Story story);
}
