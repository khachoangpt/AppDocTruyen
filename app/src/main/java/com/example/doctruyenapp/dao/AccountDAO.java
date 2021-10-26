package com.example.doctruyenapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.Story;

import java.util.List;

@Dao
public interface AccountDAO {
    //Get all accounts
    @Query("SELECT * FROM account")
    List<Account> getAllAccount();

    //Get story by id
    @Query("SELECT * FROM account WHERE accountId = :id")
    Account getAccountById(int id);

    //add new account into db
    @Insert
    void addAccount(Account... acc);
}
