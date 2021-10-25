package com.example.doctruyenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class ContentActivity extends AppCompatActivity {
    TextView txtTitle, txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        txtTitle = findViewById(R.id.tv_title);
        txtContent = findViewById(R.id.tv_content);

        //Get data of story
        Intent intent = getIntent();
        String title = intent.getStringExtra("tentruyen");
        String content = intent.getStringExtra("noidung");

        txtTitle.setText(title);
        txtContent.setText(content);

        //Allow scroll
        txtContent.setMovementMethod(new ScrollingMovementMethod());
    }
}