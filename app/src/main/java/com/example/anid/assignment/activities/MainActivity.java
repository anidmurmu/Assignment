package com.example.anid.assignment.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.anid.assignment.adapters.MainActivitySectionAdapter;
import com.example.anid.assignment.api.model.Data;
import com.example.anid.assignment.api.service.DataClient;
import com.example.anid.assignment.R;
import com.example.anid.assignment.api.service.ServiceGenerator;
import com.example.anid.assignment.helpers.DeviceUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // Vertical recyclerview for each section
    private RecyclerView mRecyclerViewSection;

    // Contains all data from api response
    private Data mData;
    private ConstraintLayout mNoInternetConn;
    private SwipeRefreshLayout mContentSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.categories);

        mNoInternetConn = findViewById(R.id.img_no_internet);
        mContentSwipeRefreshLayout = findViewById(R.id.refrest_layout);
        mContentSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mRecyclerViewSection = findViewById(R.id.recycler_view_section);
        mRecyclerViewSection.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        refreshView();

        mContentSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshView();
                    }
                }
        );
    }

    private void refreshView() {
        mContentSwipeRefreshLayout.setRefreshing(true);
        if(DeviceUtils.hasInternetAccess(MainActivity.this)) {
            mNoInternetConn.setVisibility(View.GONE);
            mRecyclerViewSection.setVisibility(View.VISIBLE);
            getData();
        } else {
            Toast.makeText(this, R.string.no_internet_connnection, Toast.LENGTH_SHORT).show();
            mRecyclerViewSection.setVisibility(View.GONE);
            mNoInternetConn.setVisibility(View.VISIBLE);

        }
        mContentSwipeRefreshLayout.setRefreshing(false);
    }


    private void getData() {

        DataClient dataClient = ServiceGenerator.createService(DataClient.class);
        Call<Data> call = dataClient.getData();

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    if(response.body().getCode() != 200) {
                        Toast.makeText(MainActivity.this, R.string.api_call_error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mData = response.body();
                    MainActivitySectionAdapter mainActivitySectionAdapter = new MainActivitySectionAdapter(MainActivity.this, mData);
                    mRecyclerViewSection.setAdapter(mainActivitySectionAdapter);
                } else {
                    Toast.makeText(MainActivity.this, R.string.api_response_error, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

                // handle error
                if(t instanceof IOException) {
                    Toast.makeText(MainActivity.this, R.string.internet_response_error, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, R.string.unknown, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
