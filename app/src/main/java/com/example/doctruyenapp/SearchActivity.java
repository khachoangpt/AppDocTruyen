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

import com.example.doctruyenapp.adapter.AdapterBook;

import com.example.doctruyenapp.model.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ListView listviewSearch;
    EditText edtSearch;
    ArrayList<Book> listBook;
    ArrayList<Book> listSearch;
    AdapterBook adapterBook;
  //  AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       // db = AppDatabase.getInstance(this);

        listviewSearch = findViewById(R.id.listview);
        edtSearch = findViewById(R.id.edt_search);

        //iniList();

        listviewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               // Intent intent = new Intent(SearchActivity.this, ContentActivity.class);
                String title = listSearch.get(position).title;
             //   String content = listSearch.get(position).content;
//                intent.putExtra("tentruyen", title);
//                intent.putExtra("noidung", content);
//                startActivity(intent);
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

        ArrayList<Book> filterList = new ArrayList<>();
        for (Book item : listBook) {
            if (item.title.toLowerCase().contains(text.toLowerCase())) {
                //Add to filterList
                filterList.add(item);

                //Add to array
                listSearch.add(item);
            }
        }
        adapterBook.filterList(filterList);
    }

    //Get data and add to listview
//    private void iniList() {
//        listBook = new ArrayList<>();
//        listSearch = new ArrayList<>();
//
//        List<Book> BookList = db.BookDAO().getAllBook();
//
//        for (Book Book : BookList) {
//            int id = Book.id;
//            String title = Book.title;
//            String content = Book.content;
//            String image = Book.image;
//            listBook.add(new Book(id, title, content, image));
//            listSearch.add(new Book(id, title, content, image));
//            adapterBook = new AdapterBook(getApplicationContext(), listBook);
//            listviewSearch.setAdapter(adapterBook);
//        }
//    }
}