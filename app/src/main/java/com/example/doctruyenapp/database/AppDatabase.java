package com.example.doctruyenapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.doctruyenapp.dao.AccountDAO;
import com.example.doctruyenapp.dao.AccountStoryDAO;
import com.example.doctruyenapp.dao.StoryDAO;
import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.AccountStory;
import com.example.doctruyenapp.model.Story;


@Database(entities = {Account.class, Story.class, AccountStory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "readingStory")
                    .createFromAsset("database/readingStory.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract AccountDAO accountDAO();
    public abstract StoryDAO storyDAO();
    public abstract AccountStoryDAO accountStoryDAO();
}
