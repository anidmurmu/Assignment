package com.example.anid.assignment.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.anid.assignment.adapters.MainActivityAdapter;
import com.example.anid.assignment.adapters.MainActivitySectionAdapter;
import com.example.anid.assignment.api.model.Content;
import com.example.anid.assignment.api.model.Data;
import com.example.anid.assignment.api.service.DataClient;
import com.example.anid.assignment.R;
import com.example.anid.assignment.api.service.ServiceGenerator;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mSectionRecyclerView;

    private Data mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionRecyclerView = findViewById(R.id.section_recyler_view);
        mSectionRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getData();
    }

    private void getData() {

        DataClient dataClient = ServiceGenerator.createService(DataClient.class);
        Call<Data> call = dataClient.getData();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                //Log.d("apple1 ", response.code() + "");
                if(response.isSuccessful()) {
                    if(response.body().getCode() != 200) {
                        Toast.makeText(MainActivity.this, "Api call error", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mData = response.body();
                    MainActivitySectionAdapter mainActivitySectionAdapter = new MainActivitySectionAdapter(MainActivity.this, mData);
                    mSectionRecyclerView.setAdapter(mainActivitySectionAdapter);
                    //Log.d("apple content ", mData.getCode() + "");
                    //List<Content> contents = mData.getContent();
                    /*for (Content content : contents) {
                        *//*for (Product product : content.getProducts()) {
                            Log.d("apple sectionT:", product.get;
                        }*//*
                        Log.d("apple sectitonT:", content.getBannerImage() + "");
                        Log.d("apple sectitonT:", content.getFirstImage() + "");
                        Log.d("apple sectitonT:", content.getSecondImage() + "");
                        Log.d("apple :", "*****************");
                    }*/
                } else {
                    Toast.makeText(MainActivity.this, "Api call error1", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

                // handle error
                if(t instanceof IOException) {
                    Toast.makeText(MainActivity.this, "Api call error2", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
