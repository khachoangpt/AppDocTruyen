package com.example.doctruyenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyenapp.adapter.AdapterBook;
import com.example.doctruyenapp.api.ApiService;
import com.example.doctruyenapp.model.Book;
import com.example.doctruyenapp.model.ErrorException;
import com.example.doctruyenapp.utils.PreferrenceUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookCategoryActivity extends AppCompatActivity {

    ListView lvBookCategory;
    ArrayList<Book> listBook;
    AdapterBook adapterBook;
    TextView tvBookCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_category);
        lvBookCategory = findViewById(R.id.lvBookCategory);
        initViews();


    }

    private void initViews() {
        ApiService.apiService.isTokenValidation(PreferrenceUtils.getJwt(getApplicationContext())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 401) {
                    Intent intent = new Intent(BookCategoryActivity.this, LoginActivity.class);
                    intent.putExtra("session", "expired");
                    startActivity(intent);
                    finish();
                }
                else if (response.body()) {
                    mapping();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(BookCategoryActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Add data to listview
    private void mapping() {
        tvBookCategory = findViewById(R.id.tvBookCategory);
        listBook = new ArrayList<>();
        Intent intent = getIntent();
        String name = intent.getStringExtra("categoryName");
        long id = intent.getLongExtra("categoryId", 1);
        tvBookCategory.setText(name);
        ApiService.apiService.getAllBookByCategory(PreferrenceUtils.getJwt(this), id).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    listBook = (ArrayList<Book>) response.body();
                    adapterBook = new AdapterBook(getApplicationContext(), listBook);
                    lvBookCategory.setAdapter(adapterBook);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(BookCategoryActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(BookCategoryActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lvBookCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BookCategoryActivity.this, MediaActivity.class);
                intent.putExtra("id", listBook.get(i).getId());
                startActivity(intent);
            }
        });

    }

}