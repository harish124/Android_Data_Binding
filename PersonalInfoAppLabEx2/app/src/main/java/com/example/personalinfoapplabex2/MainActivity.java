package com.example.personalinfoapplabex2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.personalinfoapplabex2.databinding.ActivityMainBinding;
import com.example.personalinfoapplabex2.viewmodel.MainActivityViewModel;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel vm;
    private Calendar c;
    private DatePickerDialog dpd;

    void init() {
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.setVm(vm);
        vm.init();
        c = Calendar.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        binding.cb1.setOnClickListener(v -> {
            if (!vm.getLang().contains("" + binding.cb1.getText())) {
                vm.getLang().add("" + binding.cb1.getText());
            }
        });
        binding.cb2.setOnClickListener(v -> {
            if (!vm.getLang().contains("" + binding.cb2.getText())) {
                vm.getLang().add("" + binding.cb2.getText());
            }
        });
        binding.cb3.setOnClickListener(v -> {
            if (!vm.getLang().contains("" + binding.cb3.getText())) {
                vm.getLang().add("" + binding.cb3.getText());
            }
        });

        binding.dob.setOnClickListener(v -> {
            int day = c.get(Calendar.DAY_OF_MONTH);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            dpd = new DatePickerDialog(MainActivity.this, (view, mYear, mMonth, mDayOfMonth) -> {
                binding.dob.setText(mDayOfMonth + "/" + mMonth + "/" + mYear);
            }, day, month, year);

            dpd.show();
        });

        binding.submit.setOnClickListener(v->{
            Intent intent=new Intent(MainActivity.this,SecondPage.class);
            startActivity(intent);
        });

    }
}
