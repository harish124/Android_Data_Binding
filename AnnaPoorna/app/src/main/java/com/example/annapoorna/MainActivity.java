package com.example.annapoorna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.example.annapoorna.databinding.ActivityMainBinding;
import com.example.annapoorna.view_model.MainActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import frame_transition.Transition;
import print.Print;

public class MainActivity extends AppCompatActivity {

    private Print p;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Transition transition;
    private ActivityMainBinding activityMainBinding;
    private MainActivityViewModel loginViewModel;

    void init() {
        p = new Print(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();
        transition = new Transition(MainActivity.this);
        user=FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        loginViewModel= new ViewModelProvider(this).get(MainActivityViewModel.class);
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
                p.sprintf("Signing In"+user.getEmail());
                if(user.getEmail().toLowerCase().contains("police"))
                {
                    transition.goTo(PassengerActivity.class);
                }
                else
                {
                    transition.goTo(HomePage.class);
                }
            }
            else if(bool==false){
                p.fprintf("Error Logging in\nPlease contact the admin");
            }
        });

        loginViewModel.getSignUpSuccessfull().observe(this,(bool)->{
            if(bool)
            {
                p.sprintf("Signing In");
                transition.goTo(MainActivity.class);
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
