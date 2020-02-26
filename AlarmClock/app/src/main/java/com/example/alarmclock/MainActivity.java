package com.example.alarmclock;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.alarmclock.databinding.ActivityMainBinding;
import com.example.alarmclock.print.Print;
import com.example.alarmclock.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;
    private Print p;
    private TextView textViewSecond, textViewMinute;

    void init() {
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(MainActivity.this);
        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        vm.init();
        binding.setVm(vm);
        p = new Print(MainActivity.this);
    }

    void initProgressBar(int maxMin) {
        binding.progressBar.setMax(60);
        binding.progressBar2.setMax(maxMin);
        binding.progressBar2.setProgress(maxMin);
        binding.progressBar.setProgress(60);
    }

    void initOnclick() {
        binding.fiveMin.setOnClickListener(MainActivity.this);
        binding.tenMin.setOnClickListener(MainActivity.this);
        binding.fifteenMin.setOnClickListener(MainActivity.this);
        binding.twentyMin.setOnClickListener(MainActivity.this);
        binding.fortyfiveMin.setOnClickListener(MainActivity.this);
        binding.sixtyMin.setOnClickListener(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initProgressBar(5);
        initOnclick();

        binding.timerBtn.setOnClickListener((v) -> {
            vm.startStop();
        });

        vm.getSeconds().observe(this, (txt) -> {

            binding.progressBar.setProgress(txt);
        });

        vm.getMinutes().observe(this, (txt) -> {

            binding.progressBar2.setProgress(txt);
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fiveMin:
                binding.timerText.setText("5 : 00");
                vm.setTimerLeftInMilliseconds(300000);
                initProgressBar(5);
                break;
            case R.id.tenMin:
                binding.timerText.setText("10 : 00");
                vm.setTimerLeftInMilliseconds(600000);
                initProgressBar(10);
                break;
            case R.id.fifteenMin:
                binding.timerText.setText("15 : 00");
                vm.setTimerLeftInMilliseconds(900000);
                initProgressBar(15);
                break;
            case R.id.twentyMin:
                binding.timerText.setText("20 : 00");
                vm.setTimerLeftInMilliseconds(1200000);
                initProgressBar(20);
                break;
            case R.id.fortyfiveMin:
                binding.timerText.setText("45 : 00");
                vm.setTimerLeftInMilliseconds(2700000);
                initProgressBar(45);
                break;
            case R.id.sixtyMin:
                binding.timerText.setText("60 : 00");
                vm.setTimerLeftInMilliseconds(3600000);
                initProgressBar(60);
                break;
        }

    }
}
