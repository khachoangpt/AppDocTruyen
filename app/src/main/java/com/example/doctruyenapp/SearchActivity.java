package com.example.doctruyenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.doctruyenapp.adapter.AdapterStory;
import com.example.doctruyenapp.database.Database;
import com.example.doctruyenapp.model.Story;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    ListView listviewSearch;
    EditText edtSearch;
    ArrayList<Story> listStory;
    ArrayList<Story> listSearch;
    AdapterStory adapterStory;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listviewSearch = findViewById(R.id.listview);
        edtSearch = findViewById(R.id.edt_search);

        iniList();

        listviewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(SearchActivity.this, ContentActivity.class);
                String title = listSearch.get(position).getTitle();
                String content = listSearch.get(position).getContent();
                intent.putExtra("tentruyen", title);
                intent.putExtra("noidung", content);
                startActivity(intent);
            }
        });

        //Edit text search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    //Search
    private void filter(String text) {
        listSearch.clear();

        ArrayList<Story> filterList = new ArrayList<>();
        for (Story item : listStory) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                //Add to filterList
                filterList.add(item);

                //Add to array
                listSearch.add(item);
            }
        }
        adapterStory.filterList(filterList);
    }

    //Get data and add to listview
    private void iniList() {
        listStory = new ArrayList<>();
        listSearch = new ArrayList<>();
        database = new Database(this);

        Cursor cursor = database.getAllStory();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenTruyen = cursor.getString(1);
            String noidung = cursor.getString(2);
            String anh = cursor.getString(3);
            int id_tk = cursor.getInt(4);
            listStory.add(new Story(id, tenTruyen, noidung, anh, id_tk));
            listSearch.add(new Story(id, tenTruyen, noidung, anh, id_tk));
            adapterStory = new AdapterStory(getApplicationContext(), listStory);
            listviewSearch.setAdapter(adapterStory);
        }
        cursor.moveToFirst();
        cursor.close();
    }
}