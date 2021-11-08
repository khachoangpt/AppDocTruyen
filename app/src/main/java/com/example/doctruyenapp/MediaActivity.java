package com.example.doctruyenapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyenapp.adapter.BookCategoryAdapter;
import com.example.doctruyenapp.adapter.CommentAdapter;
import com.example.doctruyenapp.api.ApiService;
import com.example.doctruyenapp.model.Book;
import com.example.doctruyenapp.model.BookCategory;
import com.example.doctruyenapp.model.Comment;
import com.example.doctruyenapp.model.ErrorException;
import com.example.doctruyenapp.utils.PreferrenceUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MediaActivity extends AppCompatActivity {


    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private ImageView imageCover;
    private TextView tvTitle, tvContent, tvAuthor, tvLike,tvcmtContent;
    private EditText etComment;
    private Button btnLike;
    ArrayList<Comment> commentArrayList;
    private Book book;
    RecyclerView rvComment;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        initViews();
    }


    private void initViews() {
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 1);
        if (intent.getBooleanExtra("isSearch", false)) {
            ApiService.apiService.getBook(PreferrenceUtils.getJwt(this), id, true).enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.code() == 401) {
                        Intent intent = new Intent(MediaActivity.this, LoginActivity.class);
                        intent.putExtra("session", "expired");
                        startActivity(intent);
                    } else if (response.isSuccessful()) {
                        book = response.body();
                        setIconLike();
                    } else {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        ErrorException errorException = null;
                        try {
                            errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                            System.out.println(errorException.toString());
                            Toast.makeText(MediaActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    Toast.makeText(MediaActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ApiService.apiService.getBook(PreferrenceUtils.getJwt(this), id, false).enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.isSuccessful()) {
                        book = response.body();
                        setIconLike();
                    } else {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        ErrorException errorException = null;
                        try {
                            errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                            System.out.println(errorException.toString());
                            Toast.makeText(MediaActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    Toast.makeText(MediaActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void initPlayer() {
        imageCover = findViewById(R.id.imageCover);
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        tvAuthor = findViewById(R.id.tvAuthor);
        etComment = findViewById(R.id.etComment);
        Picasso.get().load(book.getImage()).into(imageCover);
        tvTitle.setText("Truyện: " + book.getTitle());
        tvContent.setText(book.getDescription());
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        tvAuthor.setText("Tác giả: " + book.getAuthor());
        playerView = findViewById(R.id.playerview);
        playerView.setControllerShowTimeoutMs(0);
        playerView.setCameraDistance(30);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "app"));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(book.getAudio()));
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        setUpComment();
        loadComment();
    }

    private void setUpComment() {
        rvComment = findViewById(R.id.rvComment);
        commentArrayList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentArrayList, this);
        rvComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvComment.setAdapter(commentAdapter);
    }

    private void loadComment() {
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 1);

        ApiService.apiService.getAllComment(PreferrenceUtils.getJwt(this), id).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()) {
                    commentArrayList.clear();
                    commentArrayList.addAll(response.body());
                    commentAdapter.notifyDataSetChanged();

                } else {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    ErrorException errorException = null;
                    try {
                        errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                        System.out.println(errorException.toString());
                        Toast.makeText(MediaActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(MediaActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage().toString());
            }
        });

    }

    private void setIconLike() {
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 1);
        ApiService.apiService.isLikeBook(PreferrenceUtils.getJwt(this), id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                btnLike = findViewById(R.id.btnLike);
                if (response.body()) {
                    btnLike.setText("Liked");
                } else {
                    btnLike.setText("Like");
                }
                setLike();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MediaActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLike() {
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 1);
        ApiService.apiService.countLike(PreferrenceUtils.getJwt(this), id).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                tvLike = findViewById(R.id.tvLike);
                tvLike.setText(response.body().toString());
                initPlayer();

            }
            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Toast.makeText(MediaActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onLike(View view) {
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 1);
        ApiService.apiService.likeBook(PreferrenceUtils.getJwt(this), id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    if (btnLike == null | tvLike == null) {
                        btnLike = findViewById(R.id.btnLike);
                        tvLike = findViewById(R.id.tvLike);
                    }
                    Long like = Long.parseLong(tvLike.getText().toString().trim());
                    if (response.body().equals("unLike")) {
                        btnLike.setText("Like");
                        tvLike.setText((--like).toString());
                    } else {
                        btnLike.setText("Liked");
                        tvLike.setText((++like).toString());
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MediaActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onComment(View view) {
        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 1);
        if (!etComment.getText().toString().equals("")) {
            Comment comment = new Comment();
            comment.setContent(etComment.getText().toString());
            comment.setBookId(id);
            ApiService.apiService.createComment(PreferrenceUtils.getJwt(this), comment).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        loadComment();
                        etComment.setText("");
                    } else {
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        ErrorException errorException = null;
                        try {
                            errorException = objectMapper.readValue(response.errorBody().string(), ErrorException.class);
                            System.out.println(errorException.toString());
                            Toast.makeText(MediaActivity.this, errorException.getErrors().get(0), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(MediaActivity.this, "Hệ thống bận!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MediaActivity.this, "Vui lòng điền nội dung!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        releasePlayer();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MediaActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


}