package com.example.doctruyenapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "account")
public class Account {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "role")
    public String role;

    public Account() {
    }

    public Account(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Account(String username, String pass) {
        this.username = username;
        this.password = pass;
    }
}
