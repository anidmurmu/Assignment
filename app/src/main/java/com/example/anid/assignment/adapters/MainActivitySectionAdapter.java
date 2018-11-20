package com.example.anid.assignment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anid.assignment.R;
import com.example.anid.assignment.api.model.Content;
import com.example.anid.assignment.api.model.Data;
import com.example.anid.assignment.api.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivitySectionAdapter extends RecyclerView.Adapter<MainActivitySectionAdapter.MyViewHolder> {

    private Context mContext;
    private Data mData;
    private List<Content> mContents;

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mSectionHeader;
        private LinearLayout mSectionLayout;
        private RecyclerView mRecyclerView;

        MyViewHolder(View view) {
            super(view);

            mSectionHeader = view.findViewById(R.id.section_header);
            mRecyclerView = view.findViewById(R.id.recycler_view_list);
        }
    }

    public MainActivitySectionAdapter(Context context, Data data) {
        mContext = context;
        mData = data;
        mContents = mData.getContent();
    }

    @NonNull
    @Override
    public MainActivitySectionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.section_list_item_product, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivitySectionAdapter.MyViewHolder myViewHolder, int i) {

        Content content = mContents.get(i);


        myViewHolder.mSectionHeader.setText(content.getName());
        MainActivityAdapter mainActivityAdapter = new MainActivityAdapter(mContext, mContents.get(i).getProducts());
        myViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        myViewHolder.mRecyclerView.setAdapter(mainActivityAdapter);



    }

    @Override
    public int getItemCount() {
        return mContents == null ? 0 : mContents.size();
    }
}
