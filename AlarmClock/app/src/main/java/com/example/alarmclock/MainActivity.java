package com.example.alarmclock;

import android.os.Bundle;
import android.os.Vibrator;
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
    private Vibrator vibrator;
    int timerBtnstate = 0;//1->start state;0->pause state

    void init() {
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(MainActivity.this);
        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        vm.init();
        binding.setVm(vm);
        p = new Print(MainActivity.this);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vm.setVibrator(vibrator);
    }

    void initProgressBar(int maxMin) {
        binding.progressBar.setMax(60);
        binding.progressBar2.setMax(maxMin);
        binding.progressBar2.setProgress(maxMin);
        binding.progressBar.setProgress(60);
    }

    void initOnclick() {
        binding.edit.setOnClickListener(this);
    }

    void configNumPicker() {
        binding.numPicker.setMinValue(1);
        binding.numPicker.setMaxValue(60);

        binding.numPicker.setOnValueChangedListener((numPicker, oldVal, newVal) -> {
            int timeInMilliSecs = newVal * 60 * 1000;
            int mins = timeInMilliSecs / 60000;
            int secs = timeInMilliSecs % 60000 / 1000;
            vm.setTimerLeftInMilliseconds(timeInMilliSecs);
            initProgressBar(mins);
            if (secs < 10) {
                binding.timerText.setText(mins + " : " + secs + "0");

            } else {
                binding.timerText.setText(mins + " : " + secs);
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initProgressBar(5);
        initOnclick();
        configNumPicker();

        binding.timerBtn.setOnClickListener((v) -> {

            vm.startStop();

            if (timerBtnstate == 0) {
                binding.timerBtn.setBackgroundResource(
                        R.drawable.ic_pause_circle_outline_black_24dp);
                timerBtnstate = 1;
                binding.edit.setEnabled(false);
            } else {
                binding.timerBtn.setBackgroundResource(
                        R.drawable.ic_play_circle_outline_black_24dp);
                timerBtnstate = 0;
                binding.edit.setEnabled(true);
            }
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
            case R.id.edit:
                if (binding.edit.getText().toString().equalsIgnoreCase("edit")) {
                    binding.numPicker.setVisibility(View.VISIBLE);
                    binding.setMins.setVisibility(View.VISIBLE);
                    binding.edit.setText("Done");
                    binding.timerText.setVisibility(View.INVISIBLE);
                    binding.timerBtn.setEnabled(false);
                } else {
                    binding.numPicker.setVisibility(View.INVISIBLE);
                    binding.setMins.setVisibility(View.INVISIBLE);
                    binding.edit.setText("Edit");
                    binding.timerText.setVisibility(View.VISIBLE);
                    binding.timerBtn.setEnabled(true);
                }
        }

    }
}
