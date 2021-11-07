package com.example.doctruyenapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Account {

    public Long id;
    public String username;
    public String password;
    public String email;
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
