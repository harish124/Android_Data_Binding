package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;

import com.example.crud.asyncTasks.GetUser;
import com.example.crud.databinding.ActivityMainBinding;
import com.example.crud.print.Print;
import com.example.crud.view_model.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;
    private Print p;

    void init(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        vm= new ViewModelProvider(this).get(MainActivityViewModel.class);
        vm.init(MainActivity.this,vm);
        binding.setViewModel(vm);
        binding.setLifecycleOwner(this);
        p=new Print(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        vm.getUid().observe(this,(uid)->{
            if(uid.isEmpty()){
                return;
            }
            int id=Integer.parseInt(""+uid);

            //p.sprintf("Reached Here");
            new GetUser(MainActivity.this,id,binding).execute();
            //p.sprintf("Reached Here too");

        });

        vm.getName().observe(this,(name)->{
            //p.sprintf(""+name);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.getAllUsers();
    }
}
