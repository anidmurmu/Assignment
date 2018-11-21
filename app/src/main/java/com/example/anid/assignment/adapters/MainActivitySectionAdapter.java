package com.example.anid.assignment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anid.assignment.R;
import com.example.anid.assignment.api.model.Content;
import com.example.anid.assignment.api.model.Data;
import com.example.anid.assignment.api.model.Product;

import java.util.List;

public class MainActivitySectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Data mData;
    private List<Content> mContents;

    private static final int PRODUCT_SECTION = R.layout.section_list_item_product;
    private static final int BANNER_SECTION = R.layout.list_item_banner;
    private static final int SPLIT_BANNER_SECTION = R.layout.list_item_split;

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mSectionHeader;
        private List<Product> mProducts;
        private RecyclerView mRecyclerView;

        MyViewHolder(View view) {
            super(view);

            mSectionHeader = view.findViewById(R.id.section_header);
            mRecyclerView = view.findViewById(R.id.recycler_view_list);
        }

        public void bind(Content content) {
            mSectionHeader.setText(content.getName());
            mProducts = content.getProducts();

            MainActivityAdapter mainActivityAdapter = new MainActivityAdapter(mContext, mProducts);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            mRecyclerView.setAdapter(mainActivityAdapter);
        }
    }

    class MyViewHolderBanner extends RecyclerView.ViewHolder {

        ImageView mSectionBanner;
        public MyViewHolderBanner(View view) {
            super(view);

            mSectionBanner = view.findViewById(R.id.section_banner);
        }

        public void bind(String url) {

            Glide.with(mContext).load(url)
                    .into(mSectionBanner);
        }
    }

    class MyViewHolderSplitBanner extends RecyclerView.ViewHolder {

        ImageView mFirstImage;
        ImageView mSecondImage;
        public MyViewHolderSplitBanner(View view) {
            super(view);

            mFirstImage = view.findViewById(R.id.section_first_image);
            mSecondImage = view.findViewById(R.id.section_second_image);
        }

        public void bind(String firstImageUrl, String secondImageUrl) {

            Glide.with(mContext).load(firstImageUrl)
                    .into(mFirstImage);

            Glide.with(mContext).load(secondImageUrl)
                    .into(mSecondImage);
        }
    }


    public MainActivitySectionAdapter(Context context, Data data) {
        mContext = context;
        mData = data;
        mContents = mData.getContent();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
        RecyclerView.ViewHolder holder;

        if(viewType == PRODUCT_SECTION) {
            view = layoutInflater.inflate(R.layout.section_list_item_product, viewGroup, false);
            holder = new MyViewHolder(view);
        } else if(viewType == BANNER_SECTION) {
            view = layoutInflater.inflate(R.layout.list_item_banner, viewGroup, false);
            holder = new MyViewHolderBanner(view);

        } else {
            view = layoutInflater.inflate(R.layout.list_item_split, viewGroup, false);
            holder = new MyViewHolderSplitBanner(view);

        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        Content content = mContents.get(i);

        String sectionType = content.getSectionType();

        if(sectionType.equalsIgnoreCase("horizontalFreeScroll")) {
            ((MyViewHolder)holder).bind(content);
        } else if(sectionType.equalsIgnoreCase("banner")) {
            ((MyViewHolderBanner)holder).bind(content.getBannerImage());
        } else if(sectionType.equalsIgnoreCase("splitBanner")) {
            ((MyViewHolderSplitBanner)holder).bind(content.getFirstImage(), content.getSecondImage());
        }


    }

    @Override
    public int getItemCount() {
        return mContents == null ? 0 : mContents.size();
    }

    @Override
    public int getItemViewType(int position) {

        Content content = mContents.get(position);
        String sectionType = content.getSectionType();

        int viewType = 0;

        if(sectionType.equalsIgnoreCase("horizontalFreeScroll")) {
            viewType = PRODUCT_SECTION;
        } else if(sectionType.equalsIgnoreCase("banner")) {
            viewType = BANNER_SECTION;
        } else if(sectionType.equalsIgnoreCase("splitBanner")) {
            viewType = SPLIT_BANNER_SECTION;
        }

        return viewType;
    }
}
