package com.example.cashit.viewmodels;

import android.view.View;

import print.Print;

public class ProductRVAdapter2Helper {

    //Don't forget to set this class in ProdRVAdapter2 in on Bind View holder
    public void onProdImgClicked(View v, String url)
    {
        Print p=new Print(v.getContext());
        p.sprintf("Image Clicked");
    }
}
