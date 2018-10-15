package com.example.consultants.examapp.api;

import com.example.consultants.examapp.models.RandomResponse;
import com.example.consultants.examapp.models.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RandomAPI {


    // base_url/ + api
    // https://randomuser.me/api
    @GET("api")
    Call<RandomResponse> getRandomUser();


    // https://randomuser.me/api?results={count}
    @GET("api")
    Call<RandomResponse> getRandomUsers(@Query("results") int count);

    // https://randomuser.me/api/user/{id}
    // for show
    @GET("api/user")
    Call<RandomResponse> getUser(@Path("id") String id);

    @GET("api")
    Call<List<Result>> getResult();
}
