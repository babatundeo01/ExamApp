package com.example.consultants.examapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
                resultList = new ArrayList<>();
                if (response.isSuccessful()){
                    RandomResponse randomUser = response.body();
                    if (randomUser != null){
                        
                        for(int i = 0; i < randomUser.getResults().size(); i++){
                            resultList.add(randomUser.getResults().get(i));
                        }


                        generateResultList();
                    }

                    Log.d(TAG, "onResponse: "+ resultList);
                    myRecyclerAdapter.updateDataSet(resultList);
                }
            }

            @Override
            public void onFailure(Call<RandomResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private Retrofit prepareRetrofitClient() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return client;
    }

    private void generateResultList() {
        recycler = findViewById(R.id.recycler);
        myRecyclerAdapter = new MyRecyclerAdapter(resultList);
        recycler.setAdapter(myRecyclerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
