package com.example.doctruyenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.doctruyenapp.adapter.AdapterStory;
import com.example.doctruyenapp.database.Database;
import com.example.doctruyenapp.model.Story;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    Button btnAddStory;
    ListView listView;

    ArrayList<Story> listStory;

    AdapterStory adapterStory;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listView = findViewById(R.id.listviewAdmin);
        btnAddStory = findViewById(R.id.btn_add_story);

        initList();

        btnAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = getIntent().getIntExtra("Id", 0);
                Intent intent = new Intent(AdminActivity.this, AddStoryActivity.class);
                intent.putExtra("Id_tk", id);
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
                int idTruyen = listStory.get(pos).getId();
                database.delete(idTruyen);
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
        database = new Database(this);
        Cursor cursor = database.getAllStory();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String image = cursor.getString(3);
            int id_tk = cursor.getInt(4);

            listStory.add(new Story(id, title, content, image, id_tk));
            adapterStory = new AdapterStory(getApplicationContext(), listStory);
            listView.setAdapter(adapterStory);
        }
        cursor.moveToFirst();
        cursor.close();
    }
}