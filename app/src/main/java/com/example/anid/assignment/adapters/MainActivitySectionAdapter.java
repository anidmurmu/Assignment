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

    private static final String mNoInfo = "No information availabel";

    private static final int PRODUCT_SECTION = R.layout.section_product_list_item;
    private static final int BANNER_SECTION = R.layout.section_banner_list_item;
    private static final int SPLIT_BANNER_SECTION = R.layout.section_split_banner_list_item;
    private static final int MISSING_SECTION = R.layout.section_missing_list_item;

    private static final String SECTION_TYPE_HORIZONTAL_FREE_SCROLL = "horizontalfreescroll";
    private static final String SECTION_TYPE_BANNER = "banner";
    private static final String SECTION_TYPE_SPLIT_BANNER = "splitbanner";

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mSectionHeader;
        private List<Product> mProducts;
        private RecyclerView mRecyclerView;

        MyViewHolder(View view) {
            super(view);

            mSectionHeader = view.findViewById(R.id.txt_section_header);
            mRecyclerView = view.findViewById(R.id.recycler_view_product_list);
        }

        public void bind(Content content) {
            mSectionHeader.setText(content.getName());
            mProducts = content.getProducts();

            ProductsAdapter productsAdapter = new ProductsAdapter(mContext, mProducts);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            mRecyclerView.setAdapter(productsAdapter);
        }
    }

    class MyViewHolderMissing extends RecyclerView.ViewHolder {

        TextView mInfoMissing;
        public MyViewHolderMissing(View view) {
            super(view);

            mInfoMissing = view.findViewById(R.id.txt_info_missing);
        }

        public void bind(String info) {
            mInfoMissing.setText(info);
        }
    }

    class MyViewHolderBanner extends RecyclerView.ViewHolder {

        ImageView mSectionBanner;
        public MyViewHolderBanner(View view) {
            super(view);

            mSectionBanner = view.findViewById(R.id.img_section_banner);
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

            mFirstImage = view.findViewById(R.id.img_section_first_image);
            mSecondImage = view.findViewById(R.id.img_section_second_image);
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

        switch (viewType) {
            case PRODUCT_SECTION:
                view = layoutInflater.inflate(R.layout.section_product_list_item, viewGroup, false);
                holder = new MyViewHolder(view);
                break;
            case BANNER_SECTION:
                view = layoutInflater.inflate(R.layout.section_banner_list_item, viewGroup, false);
                holder = new MyViewHolderBanner(view);
                break;
            case SPLIT_BANNER_SECTION:
                view = layoutInflater.inflate(R.layout.section_split_banner_list_item, viewGroup, false);
                holder = new MyViewHolderSplitBanner(view);
                break;
            default:
                view = layoutInflater.inflate(R.layout.section_missing_list_item, viewGroup, false);
                holder = new MyViewHolderMissing(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        Content content = mContents.get(i);

        String sectionType = content.getSectionType();
        sectionType = sectionType.toLowerCase().trim();

        switch (sectionType) {
            case SECTION_TYPE_HORIZONTAL_FREE_SCROLL:
                ((MyViewHolder)holder).bind(content);
                break;
            case SECTION_TYPE_BANNER:
                ((MyViewHolderBanner)holder).bind(content.getBannerImage());
                break;
            case SECTION_TYPE_SPLIT_BANNER:
                ((MyViewHolderSplitBanner)holder).bind(content.getFirstImage(), content.getSecondImage());
                break;
            default:
                ((MyViewHolderMissing)holder).bind(mNoInfo);

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
        sectionType = sectionType.toLowerCase().trim();

        int viewType;

        switch (sectionType) {
            case SECTION_TYPE_HORIZONTAL_FREE_SCROLL:
                viewType = PRODUCT_SECTION;
                break;
            case SECTION_TYPE_BANNER:
                viewType = BANNER_SECTION;
                break;
            case SECTION_TYPE_SPLIT_BANNER:
                viewType = SPLIT_BANNER_SECTION;
                break;
            default:
                viewType = MISSING_SECTION;

        }

        return viewType;
    }
}
