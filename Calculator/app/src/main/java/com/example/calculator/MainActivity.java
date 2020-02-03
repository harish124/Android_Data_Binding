package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.calculator.databinding.ActivityMainBinding;
import com.example.calculator.view_model.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;

    private String Tag = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        vm= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        vm.init();
        binding.setLifecycleOwner(this);
        binding.setViewModel(vm);

        vm.getInpText().observe(this,(txt)->{

            Log.d(Tag,"inpText = "+txt);
            if(txt.isEmpty()){
                vm.setInpText("0");
            }

            else{
                if(!vm.getCurrOpr().getValue().equals("=")){

                    Log.d(Tag,"val1 = "+vm.getObj().getVal1());
                    if(vm.getObj().getVal1()!=-9999.0){
                        vm.setInpText(vm.getInpText().getValue().substring((int)vm.getValLength()));
                        vm.setPrevOpr(vm.getCurrOpr().getValue());
                        vm.setCurrOpr("=");

                        switchBtnBackgrounds(vm.getCurrOpr().getValue(),false);
                        Log.d(Tag,"Now"+vm.getCurrOpr().getValue());
                        Log.d(Tag,"Len = "+vm.getValLength());
                    }
                    vm.getObj().setVal1(0.0);
                    //vm.setCurrOpr("=");
                }
            }

        });



        vm.getCurrOpr().observe(this,(opr)->{

            if(!vm.getCurrOpr().getValue().equals("=")){
                switchBtnBackgrounds(vm.getCurrOpr().getValue(),true);
            }
        });

    }

    void switchBtnBackgrounds(String opr,boolean on){
        if(opr.equals("+")){
            if (on) {
                binding.buttonPlus.setBackgroundResource(R.drawable.plus_white_bkg);
                binding.buttonPlus.setTextColor(Color.RED);
            } else {
                binding.buttonPlus.setBackgroundResource(R.drawable.plus_bkg);
                binding.buttonPlus.setTextColor(Color.WHITE);
            }
        }
    }


}
