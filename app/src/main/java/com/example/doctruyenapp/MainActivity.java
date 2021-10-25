package com.example.doctruyenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.doctruyenapp.adapter.AdapterStory;
import com.example.doctruyenapp.adapter.AdapterCategory;
import com.example.doctruyenapp.adapter.AdapterInformation;
import com.example.doctruyenapp.database.Database;
import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.Category;
import com.example.doctruyenapp.model.Story;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, listViewNew, listViewInfor;
    DrawerLayout drawerLayout;
    String email, accountName;
    ArrayList<Story> storyArrayList;
    AdapterStory adapterStory;
    ArrayList<Category> categoryArrayList;
    ArrayList<Account> accountArrayList;
    Database database;
    AdapterCategory adapterCategory;
    AdapterInformation adapterInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        database = new Database(this);
        //receive data from LoginActivity
        int i = getIntent().getIntExtra("phanquyen", 0);
        int id = getIntent().getIntExtra("id", 0);
        accountName = getIntent().getStringExtra("tentk");
        email = getIntent().getStringExtra("email");

        mapping();
        actionBar();
        actionViewFlipper();

        //Click item event
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                String ten = storyArrayList.get(i).getTitle();
                String content = storyArrayList.get(i).getContent();
                intent.putExtra("tentruyen", ten);
                intent.putExtra("noidung", content);
                startActivity(intent);
            }
        });

        //Click item event for listview category
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0){ //Upload story
                    if(i == 2){
                        //Send id account to AdminActivity
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        intent.putExtra("Id",id);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Bạn không có quyền thêm truyện", Toast.LENGTH_SHORT).show();
                    }
                }else if(position == 1){ //Move to content screen
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(intent);
                }else if (position == 2){ //Logout
                    finish();
                }
            }
        });
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
        listQuangCao.add("https://toplist.vn/images/800px/rua-va-tho-230179.jpg");
        listQuangCao.add("https://toplist.vn/images/800px/deo-chuong-cho-meo-230180.jpg");
        listQuangCao.add("https://toplist.vn/images/800px/cu-cai-trang-230181.jpg");
        listQuangCao.add("https://toplist.vn/images/800px/de-den-va-de-trang-230182.jpg");
        //Import image to ImageView, ImageView to app
        for (int i = 0; i < listQuangCao.size(); i++){
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

        storyArrayList = new ArrayList<>();

        Cursor cursor = database.getThreeNewestStory();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String image = cursor.getString(3);
            int id_tk = cursor.getInt(4);
            storyArrayList.add(new Story(id, title, content, image, id_tk));
            adapterStory = new AdapterStory(getApplicationContext(), storyArrayList);
            listViewNew.setAdapter(adapterStory);
        }

        cursor.moveToFirst();
        cursor.close();

        //Information
        accountArrayList = new ArrayList<>();
        accountArrayList.add(new Account(accountName, email));
        adapterInformation = new AdapterInformation(this,R.layout.navigation_thongtin, accountArrayList);
        listViewInfor.setAdapter(adapterInformation);

        //Category
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category("Thêm truyện", R.drawable.ic_baseline_create_24));
        categoryArrayList.add(new Category("Thông tin", R.drawable.ic_baseline_face_24));
        categoryArrayList.add(new Category("Đăng Xuất", R.drawable.ic_baseline_login_24));
        adapterCategory = new AdapterCategory(this, R.layout.category, categoryArrayList);
        listView.setAdapter(adapterCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Click search icon move to SearchActivity
        switch (item.getItemId()){
            case R.id.menu1:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}