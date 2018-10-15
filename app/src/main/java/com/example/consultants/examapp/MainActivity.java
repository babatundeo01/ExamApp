package com.example.consultants.examapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.consultants.examapp.api.RandomAPI;
import com.example.consultants.examapp.models.RandomResponse;
import com.example.consultants.examapp.models.Result;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://randomuser.me/";
    private static final String TAG = "LOG_TAG";
    private Retrofit client;
    private RandomAPI randomAPI;
    private MyRecyclerAdapter myRecyclerAdapter;
    private RecyclerView recycler;
    List<Result> resultList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        client = prepareRetrofitClient();
        randomAPI = client.create(RandomAPI.class);

        randomAPI.getRandomUsers(30).enqueue(new Callback<RandomResponse>() {
            @Override
            public void onResponse(Call<RandomResponse> call, Response<RandomResponse> response) {
                if (response.isSuccessful()){
                    RandomResponse randomUser = response.body();
                      //  resultList = randomUser.getResults();

                    generateResultList((ArrayList<Result>) randomUser.getResults());

                    //Log.d(TAG, "onResponse: "+ resultList);
                    //List<Result> results = (List<Result>) resultList.get(0);
                    myRecyclerAdapter.updateDataSet(resultList);
                }
            }

            @Override
            public void onFailure(Call<RandomResponse> call, Throwable t) {
                Log.e(TAG, "onResponse: Error " + t);
            }
        });

        //myRecyclerAdapter.updateDataSet(resultList);
    }

    private Retrofit prepareRetrofitClient() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return client;
    }

    private void generateResultList(ArrayList<Result> resultList) {
        recycler = findViewById(R.id.recycler);
        myRecyclerAdapter = new MyRecyclerAdapter(this, resultList);
        recycler.setAdapter(myRecyclerAdapter);

        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
