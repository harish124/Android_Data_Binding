package com.example.cashit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.cashit.models.ProductDetails;
import com.example.cashit.my_interfaces.DataLoadListener;
import com.example.cashit.viewmodels.ProductsFragmentViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import print.Print;

public class HomePage extends AppCompatActivity implements DataLoadListener {

    @BindView(R.id.viewpagertab)
    SmartTabLayout smartTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomAppBar)
    BottomAppBar bottomAppBar;
    @BindView(R.id.homePageLayout)
    RelativeLayout homePageLayout;

    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;




    private TabLayout tabLayout;
    private TabAdapter tabAdapter;
    private ProductsFragmentViewModel viewModel;
    private Print p;
    public ArrayList<ProductDetails> products;

    void init() {
        p = new Print(this);

        //tabLayout=findViewById(R.id.tabLayout);

        //tabAdapter = new TabAdapter(getSupportFragmentManager());

        viewModel= ViewModelProviders.of(this)
                .get(ProductsFragmentViewModel.class);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Products", ProductsFragment.class)
                .add("Post", PostFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(viewPager);
        smartTabLayout.setViewPager(viewPager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        init();
        setSupportActionBar(bottomAppBar);

        //viewModel.init(this);
    }

    @Override
    public void onProductsFetchedFromFirebase() {
        p.fprintf("onProd Fetchde from firebase");
        viewModel.getProducts().observe(this,(fetchedProducts)-> {
            products=fetchedProducts;
            Log.e("Prod Name",""+products.size());
            ProductsFragment instance=ProductsFragment.getInstance();
            instance.setProducts(products);
            instance.setAdapter();

            instance.getAdapter().notifyDataSetChanged();

        });
    }

    @OnClick(R.id.floatingActionButton)
    public void fabClicked()
    {
        switch (viewPager.getCurrentItem())
        {
            case 0://Products Tab

                break;
            case 1:break;
            default:return;
        }
    }



    @OnClick(R.id.homePageLayout)
    public void rootLayoutTapped(View v) {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();    //Nothing to worry about this. It will be triggered when empty space is tapped when keyboard is not visible.
        }
    }


    @OnPageChange(R.id.viewPager)
    public void onPageChange(int pos)
    {
        switch (pos)
        {
            case 0:

                getSupportActionBar().show();
                if(bottomAppBar.getVisibility()==View.VISIBLE)
                {
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                bottomAppBar.setVisibility(View.INVISIBLE);
                floatingActionButton.setVisibility(View.INVISIBLE);
                break;
            default:return;
        }
    }



}
