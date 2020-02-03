package com.example.cashit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.cashit.databinding.ActivityMainBinding;
import com.example.cashit.models.Users;
import com.example.cashit.viewmodels.MainActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame_transition.Transition;
import print.Print;

public class MainActivity extends AppCompatActivity {

    Print p;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Transition transition;
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel loginViewModel;

    void init() {
        p = new Print(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        transition = new Transition(MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        loginViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        activityMainBinding.setLoginViewModel(loginViewModel);

        loginViewModel.init();

        loginViewModel.getValidCredentials().observe(this,(val)->{
            switch (val){
                case -1:
                    p.fprintf("Username can't be empty");
                    break;
                case  -2:
                    p.fprintf("Username must contain atleast 6 characters");
                    break;
                case -3:
                    p.fprintf("Password can't be empty");
                    break;
                case -4:
                    p.fprintf("Password must contain atleast 6 characters");
                    break;
            }
        });

        loginViewModel.getSignInSuccessfull().observe(this, (bool)->{
            if(bool)
            {
                p.sprintf("Signing In");
                transition.goTo(HomePage.class);
            }
            else if(bool==false){
                p.fprintf("Error Logging in\nPlease contact the admin");
            }
        });

        loginViewModel.getSignUpSuccessfull().observe(this,(bool)->{
            if(bool)
            {
                p.sprintf("Signing In");
                transition.goTo(HomePage.class);
            }
            else if(bool==false){
                p.fprintf("Error Creating Account");
            }
        });

        loginViewModel.getExceptionMsg().observe(this,(msg)->{
            p.fprintf("Error: "+msg);
        });


    }

    public void rootlayoutTapped(View view) {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        catch(Exception e)
        {
            e.printStackTrace();    //Nothing to worry about this. It will be triggered when empty space is tapped when keyboard is not visible.
        }
    }
}
