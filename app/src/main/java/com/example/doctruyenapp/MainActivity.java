package com.example.doctruyenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyenapp.adapter.AdapterBook;
import com.example.doctruyenapp.adapter.AdapterCategory;
import com.example.doctruyenapp.adapter.AdapterInformation;
import com.example.doctruyenapp.adapter.BookCategoryAdapter;
import com.example.doctruyenapp.api.ApiService;
import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.Book;
import com.example.doctruyenapp.model.BookCategory;
import com.example.doctruyenapp.model.Category;
import com.example.doctruyenapp.model.ErrorException;
import com.example.doctruyenapp.utils.PreferrenceUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, listViewNew, listViewInfor;
    DrawerLayout drawerLayout;
    ArrayList<Book> bookArrayList;
    AdapterBook adapterNewBook;
    ArrayList<Category> categoryArrayList;
    ArrayList<BookCategory> bookCategoryArrayList;
    ArrayList<Account> accountArrayList;
    AdapterCategory adapterCategory;
    AdapterInformation adapterInformation;
    RecyclerView rvCategory;
    BookCategoryAdapter bookCategoryAdapter;
    LinearLayout lnCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        rvCategory = findViewById(R.id.rvCategory);
        bookCategoryArrayList = new ArrayList<>();
        bookCategoryAdapter = new BookCategoryAdapter(bookCategoryArrayList, this);
        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(bookCategoryAdapter);
    }

    //actionbar vs toolbar
    private void actionBar() {
        //Support method for toolbar
        setSupportActionBar(toolbar);
        //set button for actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        //click event
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //Method for Ads with ViewFlipper


    private void actionViewFlipper() {
        //Array contain ads images
        ArrayList<String> listQuangCao = new ArrayList<>();
        listQuangCao.add("https://radiotoday.net/wp-content/uploads/2018/06/dua_tre_lac_loai_1_1.jpg");
        listQuangCao.add("https://www.vinabook.com/images/detailed/191/P65346Mbia_truoc.jpg");
        listQuangCao.add("https://bizweb.dktcdn.net/100/180/408/products/nu-si-thoi-gio-bui.jpg?v=1615989127707");
        listQuangCao.add("https://www.khaitam.com/Data/Sites/1/Product/14845/bo-cong-anh-nho.png");
        //Import image to ImageView, ImageView to app
        for (int i = 0; i < listQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            //Using Picasso
            Picasso.get().load(listQuangCao.get(i)).into(imageView);
            //Fit image
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Add image from ImageView to ViewFlipper
            viewFlipper.addView(imageView);
        }
        //Config auto run for viewFlipper in 4 second
        viewFlipper.setFlipInterval(4000);
        //run auto viewFlipper
        viewFlipper.setAutoStart(true);
        //Call animation for IN and OUT ads
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        //Call animation to viewFlipper
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);
    }

    private void mapping() {
        toolbar = findViewById(R.id.toolbarMainScreen);
        viewFlipper = findViewById(R.id.viewFlipper);
        listView = findViewById(R.id.lvMainScreen);
        listViewNew = findViewById(R.id.listviewNew);
        listViewInfor = findViewById(R.id.lv_information);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        rvCategory = findViewById(R.id.rvCategory);
        lnCategory = findViewById(R.id.lnCategory);

//        bookCategoryArrayList = new ArrayList<>();
//        bookCategoryAdapter = new BookCategoryAdapter(bookCategoryArrayList, this);
//        rvCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        rvCategory.setAdapter(bookCategoryAdapter);

        loadNewest();
   //     setUpBookCategory();
        loadAccountInfo();
        loadBookCategory();
        loadCategories();
        actionBar();
        actionViewFlipper();

        //rvCategory.setAdapter(new BookCategoryAdapter(this));

        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MediaActivity.class);
                intent.putExtra("id", bookArrayList.get(i).getId());
                startActivity(intent);
            }
        });
//        //Click item event for listview

//        rvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(MainActivity.this, MediaActivity.class);
//                intent.putExtra("id", favoriteBookList.get(i).getId());
//                startActivity(intent);
//            }
//        });
//
//        //Click item event for listview category
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, AllBookActivity.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, FavoriteBookActivity.class);
                    startActivity(intent);
                } else if (position == 2) { //Move to content screen
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(intent);
                } else if (position == 3) { //Logout
                    PreferrenceUtils.saveJwt("", getApplicationContext());
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void loadCategories() {
        //Category
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category("Tất cả sách", "https://i.ibb.co/vJpV77p/Png-Item-194483.png"));
        categoryArrayList.add(new Category("Sách yêu thích", "https://www.pngkit.com/png/full/14-141827_png-file-read-a-book-icon.png"));
        categoryArrayList.add(new Category("Thông tin ứng dụng", "https://i.ibb.co/vVfFkQ5/information.png"));
        categoryArrayList.add(new Category("Đăng Xuất", "https://i.ibb.co/9Nn9byc/log-out.png"));
        adapterCategory = new AdapterCategory(this, R.layout.category, categoryArrayList);
        listView.setAdapter(adapterCategory);
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    //
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Click search icon move to SearchActivity
        switch (item.getItemId()) {
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if (listItem != null) {
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void initViews() {

        ApiService.apiService.isTokenValidation(PreferrenceUtils.getJwt(getApplicationContext())).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 401) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.putExtra("session", "expired");
                    startActivity(intent);
                    finish();
                } else if (response.body()) {
                    mapping();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Server is busy! Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadNewest() {

        ApiService.apiService.getNewestBook(PreferrenceUtils.getJwt(this)).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful()) {
                    bookArrayList = (ArrayList<Book>) response.body();
                    adapterNewBook = new AdapterBook(getApplicationContext(), bookArrayList);
                    listViewNew.setAdapter(adapterNewBook);
                    setListViewHeightBasedOnChildren(listViewNew);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(MainActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage().toString());
            }
        });

    }

    private void setUpBookCategory() {


//        bookCategoryAdapter = new BookCategoryAdapter(bookCategoryArrayList, this);
//        rvCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        rvCategory.setAdapter(bookCategoryAdapter);
//        rvCategory.setHasFixedSize(true);
//        rvCategory.setItemAnimator(new DefaultItemAnimator());

    }


    private void loadBookCategory() {


        ApiService.apiService.getAllCategory(PreferrenceUtils.getJwt(this)).enqueue(new Callback<List<BookCategory>>() {
            @Override
            public void onResponse(Call<List<BookCategory>> call, Response<List<BookCategory>> response) {
                if (response.isSuccessful()) {
                    bookCategoryArrayList.clear();
                    bookCategoryArrayList.addAll(response.body());
                    bookCategoryAdapter.notifyDataSetChanged();
                    System.out.println("category Lsit:" + bookCategoryArrayList.size());
                    //bookCategoryAdapter.setBookCategories(bookCategoryArrayList);


                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(MainActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BookCategory>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage().toString());
            }
        });

    }

    private void loadAccountInfo() {

        ApiService.apiService.getAccountInfo(PreferrenceUtils.getJwt(this)).enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if (response.isSuccessful()) {
                    accountArrayList = new ArrayList<>();
                    accountArrayList.add(response.body());
                    adapterInformation = new AdapterInformation(MainActivity.this, R.layout.navigation_thongtin, accountArrayList);
                    listViewInfor.setAdapter(adapterInformation);
                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(MainActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage().toString());
            }
        });
    }


    @Override
    public void onBackPressed() {
    }

}