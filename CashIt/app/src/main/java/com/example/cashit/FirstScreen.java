package com.example.cashit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.cashit.databinding.ActivityFirstScreenBinding;

import frame_transition.Transition;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);


        ActivityFirstScreenBinding binding=DataBindingUtil.setContentView(this,R.layout.activity_first_screen);
        binding.pressBtn.setOnClickListener((View v)->{
            Transition t=new Transition(v.getContext());
            t.goTo(MainActivity.class);
        });

    }

}
