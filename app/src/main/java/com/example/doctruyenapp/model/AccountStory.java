package com.example.doctruyenapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class AccountStory {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(defaultValue = "0")
    public int accountId;
    @ColumnInfo(defaultValue = "0")
    public int storyId;

    public AccountStory() {
    }

    public AccountStory(int accountId, int storyId) {
        this.accountId = accountId;
        this.storyId = storyId;
    }
}
