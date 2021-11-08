package com.example.doctruyenapp;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    ListView listviewSearch;
    EditText edtSearch;
    ArrayList<Book> listHistorySearch;
    AdapterBook adapterBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listviewSearch = findViewById(R.id.listview);
        edtSearch = findViewById(R.id.edt_search);
        initList();

        listviewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, MediaActivity.class);
                intent.putExtra("id", listHistorySearch.get(i).getId());
                intent.putExtra("isSearch", true);
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
        listHistorySearch.clear();
        ApiService.apiService.searchBook(PreferrenceUtils.getJwt(this), text).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.code() == 401) {
                    Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                    intent.putExtra("session", "expired");
                    startActivity(intent);
                    finish();
                } else if (response.isSuccessful()) {
                    listHistorySearch = (ArrayList<Book>) response.body();
                    setupListView();
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(SearchActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Get data and add to listview
    private void initList() {
        listHistorySearch = new ArrayList<>();
        ApiService.apiService.getHistory(PreferrenceUtils.getJwt(this)).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.code() == 401) {
                    Intent intent = new Intent(SearchActivity.this, LoginActivity.class);
                    intent.putExtra("session", "expired");
                    startActivity(intent);
                    finish();
                } else if (response.isSuccessful()) {
                    listHistorySearch = response.body().stream()
                            .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(Book::getId))), ArrayList::new));
                    setupListView();
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(SearchActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupListView() {
        adapterBook = new AdapterBook(getApplicationContext(), listHistorySearch);
        listviewSearch.setAdapter(adapterBook);
    }
}