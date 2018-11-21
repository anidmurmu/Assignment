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

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context mContext;

    // Contains list of products for each section product
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

            // Setting product image with Glide library
            Glide.with(mContext).load(product.getImageURL())
                    .into(mProductImage);

            mProductName.setText(product.getName());
            mProductPrice.setText(product.getPrice() + "");
            mProductType.setText(product.getType());
        }
    }

    public ProductsAdapter(Context context, List<Product> productsList) {
        mContext = context;
        mProductsList = productsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_list_item, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Product product = mProductsList.get(i);
        myViewHolder.bind(product);
    }

    @Override
    public int getItemCount() {
        return mProductsList == null ? 0 : mProductsList.size();
    }
}
