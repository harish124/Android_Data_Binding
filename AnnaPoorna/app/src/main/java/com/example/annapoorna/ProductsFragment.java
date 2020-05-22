package com.example.annapoorna;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.annapoorna.adapters.ProductRecyclerViewAdapter;
import com.example.annapoorna.models.ProductDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import print.Print;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductsFragment extends Fragment{

    @BindView(R.id.prodRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.searchView)
    SearchView searchView;



    private enum State {
        userState,
        globalState
    }

    private enum sortState
    {
        lowToHigh,
        HighToLow,
        randomState
    }

    private enum DatePosted{
        lowToHigh,
        HighToLow,
        randomState
    }

    private State s;
    private sortState sortState;
    private DatePosted sortDate;
    private ArrayList<ProductDetails> products;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mReference;
    private ValueEventListener valueEventListener;
    private Print p;
    private View prodView;
    private ProductRecyclerViewAdapter adapter;


    static ProductsFragment instance;


    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment getInstance(){
        if(instance!=null){
            return instance;
        }
        return new ProductsFragment();
    }

    public void setProducts(ArrayList<ProductDetails> products) {
        this.products = products;
    }

    public ProductRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        this.adapter=new ProductRecyclerViewAdapter(products);
    }

    private void init(View v) {
        products = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mReference = firebaseDatabase.getReference("Products");
        p = new Print(v.getContext());
        s= State.userState;
        sortState=sortState.randomState;
        sortDate= DatePosted.randomState;

    }

    void configRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new ProductRecyclerViewAdapter(products);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prodView = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, prodView);
        init(prodView);
        configRecyclerView();
        p.sprintf("onCreateView");
        setHasOptionsMenu(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchForThisString(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchForThisString(query);
                return false;
            }
        });


        return prodView;
    }


    void searchForThisString(String word) {
        //p.sprintf("Word = "+word);
        ValueEventListener searchValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDetails productDetails;

                products.clear();
                if(s== State.userState)
                {
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        for (DataSnapshot userProd : users.getChildren()) {
                            try {
                                productDetails = userProd.getValue(ProductDetails.class);

                                if (productDetails.getPname().toLowerCase().contains(word.toLowerCase())) {
                                    //p.sprintf("ProductDetails = "+productDetails.getCost());
                                    products.add(productDetails);
                                }
                            } catch (Exception e) {
                                Log.d("searchForThisString",e.getMessage());
                            }

                        }

                    }
                }
                if(s== State.globalState)
                {
                    //p.fprintf(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        for (DataSnapshot userProd : users.getChildren()) {
                            productDetails = userProd.getValue(ProductDetails.class);
                            try {
                                if(productDetails.getCompany().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()))
                                {
                                    if (productDetails.getPname().toLowerCase().contains(word.toLowerCase())) {
                                        //p.sprintf("ProductDetails = "+productDetails.getCost());
                                        products.add(productDetails);
                                    }
                                }
                            } catch (Exception e) {
                                Log.d("searchForThisString",e.getMessage());
                            }

                        }

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mReference.addListenerForSingleValueEvent(searchValueEventListener);

    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //inflater.inflate(R.menu.products_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void fabClicked()
    {

        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        ValueEventListener searchValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ProductDetails productDetails = new ProductDetails();

                products.clear();
                if(s== State.userState)
                {
                    p.sprintf("Displaying your Posts");
                    products.clear();
                    s= State.globalState;
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        for (DataSnapshot userProd : users.getChildren()) {
                            productDetails = userProd.getValue(ProductDetails.class);
                            if (productDetails.getCompany().equalsIgnoreCase(mUser.getUid().toString())){
                                //p.sprintf("ProductDetails = "+productDetails.getCost());
                                products.add(productDetails);
                            }

                        }

                    }
                }
                else if(s== State.globalState)
                {
                    p.sprintf("Displaying all Posts");
                    s= State.userState;
                    //p.fprintf(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    for (DataSnapshot users : dataSnapshot.getChildren()) {
                        for (DataSnapshot userProd : users.getChildren()) {
                            productDetails = userProd.getValue(ProductDetails.class);
                            products.add(productDetails);
                        }

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        mReference.addListenerForSingleValueEvent(searchValueEventListener);
    }



}
