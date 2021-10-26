package com.example.doctruyenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doctruyenapp.adapter.AdapterStory;
import com.example.doctruyenapp.database.AppDatabase;
import com.example.doctruyenapp.model.Story;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    Button btnAddStory;
    ListView listView;
    ArrayList<Story> listStory;
    AdapterStory adapterStory;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = AppDatabase.getInstance(this);

        listView = findViewById(R.id.listviewAdmin);
        btnAddStory = findViewById(R.id.btn_add_story);

        initList();

        btnAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getIntExtra("Id", 0);
                Intent intent = new Intent(AdminActivity.this, AddStoryActivity.class);
                intent.putExtra("Id", id);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                showDeleteDialog(pos);
                return false;
            }
        });
    }

    //Show delete dialog
    private void showDeleteDialog(int pos) {
        //create obj dialog
        Dialog dialog = new Dialog(this);
        //add layout to dialog
        dialog.setContentView(R.layout.delete_dialog);
        //disable outside click, click "No" to close dialog
        dialog.setCanceledOnTouchOutside(false);

        //Mapping
        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int storyId = listStory.get(pos).id;
                Story story = db.storyDAO().getStoryById(storyId);
                db.storyDAO().delete(story);
                //Update activity
                Intent intent = new Intent(AdminActivity.this, AdminActivity.class);
                finish();
                startActivity(intent);
                Toast.makeText(AdminActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    //Add data to listview
    private void initList() {
        listStory = new ArrayList<>();
        List<Story> storyList = db.storyDAO().getAllStory();

        for (Story story : storyList) {
            int id = story.id;
            String title = story.title;
            String content = story.content;
            String image = story.image;

            listStory.add(new Story(id, title, content, image));
            adapterStory = new AdapterStory(getApplicationContext(), listStory);
            listView.setAdapter(adapterStory);
        }
    }
}