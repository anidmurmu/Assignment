package com.example.anid.assignment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anid.assignment.R;
import com.example.anid.assignment.api.model.Product;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> mProductsList;

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView mProductImage;
        TextView mProductName;
        TextView mProductPrice;
        TextView mProductType;

        private MyViewHolder(View view) {
            super(view);

            mProductImage = view.findViewById(R.id.product_image);
            mProductName = view.findViewById(R.id.product_name);
            mProductPrice = view.findViewById(R.id.product_price);
            mProductType = view.findViewById(R.id.product_type);
        }

        public void bind(Product product) {
            Glide.with(mContext).load(product.getImageURL())
                    .into(mProductImage);


            mProductName.setText(product.getName());
            mProductPrice.setText(product.getPrice() + "");
            mProductType.setText(product.getType());
        }
    }

    public MainActivityAdapter(Context context, List<Product> productsList) {
        mContext = context;
        mProductsList = productsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_product, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Product product = mProductsList.get(i);


        /*Glide.with(mContext).load(product.getImageURL())
                .into(myViewHolder.mProductImage);


        myViewHolder.mProductName.setText(product.getName());
        myViewHolder.mProductPrice.setText(product.getPrice() + "");
        myViewHolder.mProductType.setText(product.getType());*/
        myViewHolder.bind(product);
    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }
}
