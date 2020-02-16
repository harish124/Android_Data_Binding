package com.example.calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculator.databinding.ActivityMainBinding;
import com.example.calculator.view_model.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;


    private String Tag = this.getClass().getSimpleName();

    void init() {
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        vm= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        vm.init();
        binding.setLifecycleOwner(this);
        binding.setViewModel(vm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        vm.getCurrOpr().observe(this,(opr)->{

            if (opr.equals("C")) {
                setdefaultBkg();
            }
            if (opr.equals("=")) {
                setdefaultBkg();
            }

            if (vm.isArithmeticOpr(opr) == 1) {
                switchBtnBackgrounds(opr, true);
                Log.d(Tag, "switchBkg = true" + "\nprevOpr = " + vm.getPrevOpr());
            } else {
                switchBtnBackgrounds(opr, false);
                Log.d(Tag, "switchBkg = false" + "\nprevOpr = " + vm.getPrevOpr());
            }
        });

    }

    void switchBtnBackgrounds(String opr,boolean on){

        if (!on) {
            setdefaultBkg();
        } else {
            //if on
            setdefaultBkg();
            if (opr.equals("+")) {
                binding.buttonPlus.setBackgroundResource(R.drawable.plus_white_bkg);
                binding.buttonPlus.setTextColor(Color.RED);
            } else if (opr.equals("-")) {
                binding.buttonMinus.setBackgroundResource(R.drawable.plus_white_bkg);
                binding.buttonMinus.setTextColor(Color.RED);
            } else if (opr.equals("*")) {
                binding.buttonMultiply.setBackgroundResource(R.drawable.op_white_bkg);
                binding.buttonMultiply.setTextColor(Color.RED);
            } else if (opr.equals("/")) {
                binding.buttonDivide.setBackgroundResource(R.drawable.op_white_bkg);
                binding.buttonDivide.setTextColor(Color.RED);
            }

        }

    }

    void setdefaultBkg() {
        binding.buttonPlus.setBackgroundResource(R.drawable.plus_bkg);
        binding.buttonPlus.setTextColor(Color.WHITE);

        binding.buttonMinus.setBackgroundResource(R.drawable.plus_bkg);
        binding.buttonMinus.setTextColor(Color.WHITE);

        binding.buttonMultiply.setBackgroundResource(R.drawable.blue_btn);
        binding.buttonMultiply.setTextColor(Color.WHITE);

        binding.buttonDivide.setBackgroundResource(R.drawable.blue_btn);
        binding.buttonDivide.setTextColor(Color.WHITE);

    }


}
