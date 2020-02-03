package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.crud.asyncTasks.Update;
import com.example.crud.databinding.ActivityUpdateBinding;
import com.example.crud.models.User;
import com.example.crud.print.Print;
import com.example.crud.view_model.MainActivityViewModel;

public class UpdateActivity extends AppCompatActivity {

    private ActivityUpdateBinding binding;
    private MainActivityViewModel vm;
    private Print p;
    void init(){
        binding= DataBindingUtil.setContentView(this,R.layout.activity_update);
        vm= new ViewModelProvider(this).get(MainActivityViewModel.class);
        vm.init(UpdateActivity.this,vm);

        Bundle extras=getIntent().getExtras();

        if(extras!=null){
            User user=new User();
            user.setName(""+extras.get("name"));
            user.setAddr(""+extras.get("addr"));
            user.setEmail(""+extras.get("email"));
            user.setPhno(""+extras.get("phno"));
            binding.uid.setText(""+extras.get("uid"));
            vm.setUser(user);
        }



        binding.setViewModel(vm);
        binding.setLifecycleOwner(this);
        p=new Print(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();

        vm.getName().observe(this,(name)->{
            new Update(UpdateActivity.this,
                    Integer.parseInt(binding.uid.getText().toString()),
                    getUser()).execute();
        });

        vm.getPhno().observe(this,(name)->{
            new Update(UpdateActivity.this,
                    Integer.parseInt(binding.uid.getText().toString()),
                    getUser()).execute();
        });

        vm.getAddr().observe(this,(name)->{
            new Update(UpdateActivity.this,
                    Integer.parseInt(binding.uid.getText().toString()),
                    getUser()).execute();
        });

        vm.getEmail().observe(this,(name)->{
            //p.sprintf(""+name);

            new Update(UpdateActivity.this,
                    Integer.parseInt(binding.uid.getText().toString()),
                    getUser()).execute();
        });
    }

    public User getUser(){
        User user=new User();
        user.setName(""+binding.name.getText());
        user.setEmail(""+binding.email.getText());
        user.setPhno(""+binding.phno.getText());
        user.setAddr(""+binding.address.getText());

        return user;
    }
}
