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
import com.example.doctruyenapp.model.AccountStory;
import com.example.doctruyenapp.model.Story;

import java.util.ArrayList;
import java.util.List;

public class FavoriteStoryActivity extends AppCompatActivity {

    AppDatabase db;
    ListView lvFavoriteStory;
    ArrayList<Story> listStory;
    AdapterStory adapterStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_story);

        db = AppDatabase.getInstance(this);
        lvFavoriteStory = findViewById(R.id.lvFavoriteStory);
        initList();

        lvFavoriteStory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(FavoriteStoryActivity.this, ContentActivity.class);
                String ten = listStory.get(i).title;
                String content = listStory.get(i).content;
                intent.putExtra("tentruyen", ten);
                intent.putExtra("noidung", content);
                startActivity(intent);
            }
        });

        lvFavoriteStory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                showDeleteDialog(pos);
                return true;
            }
        });
    }

    //Add data to listview
    private void initList() {
        listStory = new ArrayList<>();
        int accountId = getIntent().getIntExtra("Id", 0);
        List<AccountStory> listStoryId = db.accountStoryDAO().getAllStoryIdByAccountId(accountId);
        for (AccountStory accountStory : listStoryId) {
            Story story = db.storyDAO().getStoryById(accountStory.storyId);
            listStory.add(story);
        }
        adapterStory = new AdapterStory(getApplicationContext(), listStory);
        lvFavoriteStory.setAdapter(adapterStory);
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
                int accountId = getIntent().getIntExtra("Id", 0);
                int storyId = listStory.get(pos).id;
                AccountStory accountStory = db.accountStoryDAO().getAccountStoryById(accountId, storyId);
                db.accountStoryDAO().delete(accountStory);
                //Update activity
                Intent intent = new Intent(FavoriteStoryActivity.this, FavoriteStoryActivity.class);
                intent.putExtra("Id", accountId);
                finish();
                startActivity(intent);
                Toast.makeText(FavoriteStoryActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
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
}