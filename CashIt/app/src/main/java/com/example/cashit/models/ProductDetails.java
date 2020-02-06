package com.example.cashit.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ProductDetails {
    private String pname;
    private String prodImage;
    private String datePosted;
    private String company;
    private String description;
    private String cost;


    public ProductDetails() {
    }

    public ProductDetails(String pname,String prodImage, String datePosted, String company, String description, String cost) {
        this.pname=pname;
        this.prodImage = prodImage;
        this.datePosted = datePosted;
        this.company = company;
        this.description = description;
        this.cost = cost;
    }
    @BindingAdapter("android:imgUrl")
    public static void loadImg(ImageView v,String url)
    {
        Glide.with(v.getContext())
                .load(url)
                .into(v);
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getProdImage() {
        return prodImage;
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}//eproductDetails
