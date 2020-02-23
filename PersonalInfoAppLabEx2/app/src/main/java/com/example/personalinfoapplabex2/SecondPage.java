package com.example.personalinfoapplabex2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.personalinfoapplabex2.databinding.ActivitySecondPageBinding;
import com.example.personalinfoapplabex2.viewmodel.MainActivityViewModel;

public class SecondPage extends AppCompatActivity {

    private ActivitySecondPageBinding binding;
    private MainActivityViewModel vm;

    void init()
    {
        binding= DataBindingUtil.setContentView(SecondPage.this,R.layout.activity_second_page);
        binding.setLifecycleOwner(this);
        vm=new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.setVm(vm);
        vm.init();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
}
