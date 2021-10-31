package com.example.doctruyenapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.doctruyenapp.model.AccountStory;
import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.Story;

import java.util.List;

@Dao
public interface AccountStoryDAO {
    //Get all storyId by accountId
    @Query("SELECT * FROM account_story WHERE accountId = :id")
    List<AccountStory> getAllStoryIdByAccountId(int id);

    //Get top 3 storyId by accountId
    @Query("SELECT * FROM account_story WHERE accountId = :id LIMIT 3")
    List<AccountStory> getTopThreeStoryIdByAccountId(int id);

    //Get AccountStory by id
    @Query("SELECT * FROM account_story WHERE accountId = :accountId AND storyId = :storyId")
    AccountStory getAccountStoryById(int accountId, int storyId);

    //Add new AccountStory into account_story
    @Insert
    void add(AccountStory... accountStory);

    //Delete
    @Delete
    void delete(AccountStory accountStory);
}
