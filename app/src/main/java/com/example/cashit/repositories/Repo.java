package com.example.cashit.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cashit.models.ProductDetails;
import com.example.cashit.my_interfaces.DataLoadListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Repo {
    static Repo repo;
    private static Context mCtx;
    private static DataLoadListener dataLoadListener;
    private ArrayList<ProductDetails> products=new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;

    private ValueEventListener valueEventListener;

    public void init(){
        //products=new ArrayList<>();
        firebaseDatabase=FirebaseDatabase.getInstance();
        mReference=firebaseDatabase.getReference("Products");
        loadProducts();

    }

    public static Repo getInstance(){

        //mCtx=context;
        //dataLoadListener=(DataLoadListener)mCtx;
        if(repo==null){
            return new Repo();
        }
        return repo;
    }

    public int productListSize(){
        return products.size();
    }

    public MutableLiveData<ArrayList<ProductDetails>> getProducts(){

        MutableLiveData<ArrayList<ProductDetails>> prodList=new MutableLiveData<>();
        prodList.setValue(products);

        return prodList;
    }

    public void loadProducts() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //p.sprintf("This is happening again");
                Log.e("onDataChange","This is happening again");
                ProductDetails productDetails;

                products.clear();
                for (DataSnapshot users : dataSnapshot.getChildren()) {
                    for (DataSnapshot userProd : users.getChildren()) {
                        productDetails = userProd.getValue(ProductDetails.class);
                        products.add(productDetails);
                    }

                }
                //dataLoadListener.onProductsFetchedFromFirebase();
                //recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //p.fprintf("Read from firebase error");
                //p.fprintf("Error: " + databaseError.getMessage());
                Log.e("FirebaseReadError", databaseError.getMessage());
            }
        };

        mReference.addValueEventListener(valueEventListener);

    }
}
