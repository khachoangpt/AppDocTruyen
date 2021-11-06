package com.example.doctruyenapp.api;

import com.example.doctruyenapp.model.Account;
import com.example.doctruyenapp.model.Book;
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
            .baseUrl("http://9025-14-232-146-165.ngrok.io/api/v1/")
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

    @GET("book/newest")
    Call<List<Book>> getNewestBook(@Header("Authorization") String token);

    @GET("book/liked")
    Call<List<Book>> getLikedBook(@Header("Authorization") String token);

    @GET("book/{id}")
    Call<Book> getBook(@Header("Authorization") String token, @Path("id") long id);

    @POST("book/like")
    Call<String> likeBook(@Header("Authorization") String token, @Query("id") long id);

    @GET("book/likes")
    Call<Long> countLike(@Header("Authorization") String token, @Query("id") long id);


    @GET("book/isLike")
    Call<Boolean> isLikeBook(@Header("Authorization") String token, @Query("id") long id);


}
////