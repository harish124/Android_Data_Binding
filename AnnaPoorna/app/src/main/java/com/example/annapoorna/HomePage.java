package com.example.annapoorna;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.annapoorna.models.ProductDetails;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import print.Print;

public class HomePage extends AppCompatActivity {

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

//    private TabAdapter tabAdapter;
//    private ProductsFragmentViewModel viewModel;
    private Print p;
    public ArrayList<ProductDetails> products;

    void init() {
        p = new Print(this);
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Products", ProductsFragment.class)
                .add("Post", PostFragment.class)
                .add("Map",PassengerMap.class)
                .create());

        smartTabLayout.setViewPager(viewPager);
        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);
        init();
        setSupportActionBar(bottomAppBar);
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

            case 2:
                bottomAppBar.setVisibility(View.INVISIBLE);
                floatingActionButton.setVisibility(View.INVISIBLE);
                break;
            default:return;
        }
    }



}
