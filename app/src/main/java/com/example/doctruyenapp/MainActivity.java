package com.example.doctruyenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.doctruyenapp.adapter.AdapterStory;
import com.example.doctruyenapp.adapter.AdapterCategory;
import com.example.doctruyenapp.adapter.AdapterInformation;
import com.example.doctruyenapp.dao.StoryDAO;
import com.example.doctruyenapp.database.AppDatabase;
import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.AccountStory;
import com.example.doctruyenapp.model.Category;
import com.example.doctruyenapp.model.Story;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    ListView listView, listViewNew, listViewInfor, listViewFavorite;
    DrawerLayout drawerLayout;
    String email, accountName;
    int id, role;
    ArrayList<Story> storyArrayList;
    ArrayList<Story> favoriteStoryList;
    AdapterStory adapterNewStory;
    AdapterStory adapterFavoriteStory;
    ArrayList<Category> categoryArrayList;
    ArrayList<Account> accountArrayList;
    AdapterCategory adapterCategory;
    AdapterInformation adapterInformation;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        //receive data from LoginActivity
        role = getIntent().getIntExtra("phanquyen", 0);
        id = getIntent().getIntExtra("id", 0);
        accountName = getIntent().getStringExtra("tentk");
        email = getIntent().getStringExtra("email");

        mapping();
        actionBar();
        actionViewFlipper();

        //Click item event for listview new story
        listViewNew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                String ten = storyArrayList.get(i).title;
                String content = storyArrayList.get(i).content;
                intent.putExtra("tentruyen", ten);
                intent.putExtra("noidung", content);
                startActivity(intent);
            }
        });

        listViewNew.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showLikeDialog(position);
                return true;
            }
        });

        //Click item event for listview favorite
        listViewFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                String ten = favoriteStoryList.get(i).title;
                String content = favoriteStoryList.get(i).content;
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
                    if(role == 2){
                        //Send id account to AdminActivity
                        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
                        intent.putExtra("Id",id);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Bạn không có quyền thêm truyện", Toast.LENGTH_SHORT).show();
                    }
                }else if(position == 1) {
                    //Send id account to AdminActivity
                    Intent intent = new Intent(MainActivity.this, FavoriteStoryActivity.class);
                    intent.putExtra("Id",id);
                    startActivity(intent);
                }else if(position == 2){ //Move to content screen
                    Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                    startActivity(intent);
                }else if (position == 3){ //Logout
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
        listViewFavorite = findViewById(R.id.listviewMyStory);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);

        storyArrayList = new ArrayList<>();

        List<Story> storyList = db.storyDAO().getThreeNewestStory();
        for (Story story : storyList) {
            storyArrayList.add(story);
        }
        adapterNewStory = new AdapterStory(getApplicationContext(), storyArrayList);
        listViewNew.setAdapter(adapterNewStory);
        setListViewHeightBasedOnChildren(listViewNew);

        favoriteStoryList = new ArrayList<>();
        List<AccountStory> storyIdList = db.accountStoryDAO().getTopThreeStoryIdByAccountId(id);
        for (AccountStory accountStory : storyIdList) {
            int storyId = accountStory.storyId;
            Story story = db.storyDAO().getStoryById(storyId);
            favoriteStoryList.add(story);
        }
        adapterFavoriteStory = new AdapterStory(getApplicationContext(), favoriteStoryList);
        listViewFavorite.setAdapter(adapterFavoriteStory);
        setListViewHeightBasedOnChildren(listViewFavorite);

        //Information
        accountArrayList = new ArrayList<>();
        accountArrayList.add(new Account(accountName, email));
        adapterInformation = new AdapterInformation(this,R.layout.navigation_thongtin, accountArrayList);
        listViewInfor.setAdapter(adapterInformation);

        //Category
        categoryArrayList = new ArrayList<>();
        categoryArrayList.add(new Category("Thêm truyện", R.drawable.ic_baseline_create_24));
        categoryArrayList.add(new Category("Truyện của tôi", R.drawable.ic_baseline_book_24));
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

    //Like dialog
    private void showLikeDialog(int pos) {
        //create obj dialog
        Dialog dialog = new Dialog(this);
        //add layout to dialog
        dialog.setContentView(R.layout.like_dialog);
        //disable outside click, click "No" to close dialog
        dialog.setCanceledOnTouchOutside(false);

        //Mapping
        Button btnYes = dialog.findViewById(R.id.btnYes);
        Button btnNo = dialog.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int storyId = storyArrayList.get(pos).id;
                List<AccountStory> accountStories = db.accountStoryDAO().getAllStoryIdByAccountId(id);
                for (AccountStory accountStory : accountStories) {
                    if(storyId == accountStory.storyId) {
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Bạn đã thích truyện này", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                db.accountStoryDAO().add(new AccountStory(id, storyId));
                //Update activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("phanquyen", role);
                intent.putExtra("id", id);
                intent.putExtra("email",email);
                intent.putExtra("tentk", accountName);
                finish();
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
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

            if(listItem != null){
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
}