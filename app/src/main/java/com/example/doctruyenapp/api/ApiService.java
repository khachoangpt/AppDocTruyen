package com.example.doctruyenapp.api;

import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.Book;
import com.example.doctruyenapp.model.BookCategory;
import com.example.doctruyenapp.model.Comment;
import com.example.doctruyenapp.utils.PreferrenceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setLenient()
            .create();


    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://fat-dolphin-15.loca.lt/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    @POST("account/login")
    @Headers("Content-Type: application/json")
    Call<String> login(@Body Account account);

    @POST("account")
    @Headers("Content-Type: application/json")
    Call<String> signUp(@Body Account account);

    @GET("account")
    Call<Account> getAccountInfo(@Header("Authorization") String token);

    @GET("account/checkToken")
    Call<Boolean> isTokenValidation(@Header("Authorization") String token);

    @GET("book/newest")
    Call<List<Book>> getNewestBook(@Header("Authorization") String token);

    @GET("search/history")
    Call<List<Book>> getHistory(@Header("Authorization") String token);

    @GET("book/search")
    Call<List<Book>> searchBook(@Header("Authorization") String token, @Query("title") String title);

    @GET("book/liked")
    Call<List<Book>> getLikedBook(@Header("Authorization") String token);

    @GET("book")
    Call<List<Book>> getAllBook(@Header("Authorization") String token);

    @GET("book/category")
    Call<List<Book>> getAllBookByCategory(@Header("Authorization") String token, @Query("id") long id);

    @GET("category")
    Call<List<BookCategory>> getAllCategory(@Header("Authorization") String token);

    @GET("book/comment")
    Call<List<Comment>> getAllComment(@Header("Authorization") String token, @Query("id") long id);

    @POST("book/comment")
    @Headers("Content-Type: application/json")
    Call<String> createComment(@Header("Authorization") String token, @Body Comment comment);

    @GET("book/{id}")
    Call<Book> getBook(@Header("Authorization") String token, @Path("id") long id, @Query("search") Boolean search);

    @POST("book/like")
    Call<String> likeBook(@Header("Authorization") String token, @Query("id") long id);

    @GET("book/likes")
    Call<Long> countLike(@Header("Authorization") String token, @Query("id") long id);

    @GET("book/isLike")
    Call<Boolean> isLikeBook(@Header("Authorization") String token, @Query("id") long id);

}