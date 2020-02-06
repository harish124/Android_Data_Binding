package com.example.cashit.viewmodels;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cashit.models.ProductDetails;
import com.example.cashit.repositories.Repo;

import java.util.ArrayList;

import print.Print;

public class ProductsFragmentViewModel extends ViewModel {

    private MutableLiveData<String> msg,exceptionMsg;
    private Repo repo=Repo.getInstance();
    private MutableLiveData<ArrayList<ProductDetails>> products;



    private Print p;

    public void init(){
        //p=new Print(context);
        products=new MutableLiveData<>();
        repo.init();
        new Handler().postDelayed(()->{
            products=repo.getProducts();
            Log.e("SizeofProducts:",""+products.getValue().size());
        },2000);


    }

    public MutableLiveData<ArrayList<ProductDetails>> getProducts() {
        return products;
    }

    public void setProducts(ProductDetails products) {
            ArrayList<ProductDetails> obj=new ArrayList<ProductDetails>();
            obj.add(products);
            obj.add(products);

            this.products.setValue(obj);

    }
}
