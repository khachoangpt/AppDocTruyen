package com.example.doctruyenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyenapp.database.Database;
import com.example.doctruyenapp.model.Story;

public class AddStoryActivity extends AppCompatActivity {

    EditText edtTitle, edtContent, edtImage;
    Button btnUploadStory;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        edtTitle = findViewById(R.id.upload_title);
        edtContent = findViewById(R.id.upload_content);
        edtImage = findViewById(R.id.upload_story_image);

        btnUploadStory = findViewById(R.id.btn_upload);

        database = new Database(this);

        btnUploadStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String image = edtImage.getText().toString();

                if (title.equals("") || content.equals("") || image.equals("")) {
                    Toast.makeText(AddStoryActivity.this, "Chưa điền đầy đủ các phần", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = getIntent();
                    int id_tk = intent.getIntExtra("Id_tk", 0);
                    Story story = new Story(title, content, image, id_tk);
                    database.addStory(story);
                    Intent intent1 = new Intent(AddStoryActivity.this, AdminActivity.class);
                    startActivity(intent1);
                }
            }
        });
    }
}