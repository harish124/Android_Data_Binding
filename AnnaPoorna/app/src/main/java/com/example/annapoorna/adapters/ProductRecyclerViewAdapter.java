package com.example.annapoorna.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annapoorna.R;
import com.example.annapoorna.databinding.ActivityProductRecyclerViewRow2Binding;
import com.example.annapoorna.models.ProductDetails;
import com.example.annapoorna.view_model.ProductRVAdapter2Helper;


import java.util.ArrayList;

import print.Print;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.MyViewHolder>{

    private ArrayList<ProductDetails> productDetails;
    private Print p;

    public ProductRecyclerViewAdapter(ArrayList<ProductDetails> productDetails){
        this.productDetails = productDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityProductRecyclerViewRow2Binding binding= DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.activity_product_recycler_view_row2,
                parent,false
        );

        p=new Print(parent.getContext());

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.setPd(productDetails.get(position));
        holder.binding.setPdHelper(new ProductRVAdapter2Helper());

    }

    @Override
    public int getItemCount() {
        return productDetails.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ActivityProductRecyclerViewRow2Binding binding;

        private MyViewHolder(@NonNull ActivityProductRecyclerViewRow2Binding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }


}
