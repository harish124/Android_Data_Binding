package com.example.annapoorna.view_model;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.annapoorna.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class MainActivityViewModel extends ViewModel {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private MutableLiveData<String> uname=new MutableLiveData<>(),pwd=new MutableLiveData<>();
    private MutableLiveData<Boolean> signInSuccessfull,signUpSuccessfull;
    private MutableLiveData<String> exceptionMsg;
    MutableLiveData<Users> usersMutableLiveData;

    MutableLiveData<Integer> validCredentials;

    public MainActivityViewModel() {
    }

    public MutableLiveData<Boolean> getSignInSuccessfull() {
        return signInSuccessfull;
    }

    public void init() {
        mAuth = FirebaseAuth.getInstance();
        signUpSuccessfull=new MutableLiveData<>();
        signUpSuccessfull.setValue(false);

        signInSuccessfull=new MutableLiveData<>();
        signInSuccessfull.setValue(false);
        validCredentials=new MutableLiveData<>();
        exceptionMsg=new MutableLiveData<>();
        exceptionMsg.setValue("");

        uname.setValue("");
        pwd.setValue("");

        user = mAuth.getCurrentUser();
        if (user != null) {
//            p.sprintf("Welcome back "+user.getDisplayName());
            signInSuccessfull.setValue(true);
            exceptionMsg.setValue("Welcome back"+user.getEmail());
            //transition.goTo(HomePage.class);
        } else {
            exceptionMsg.setValue("User is null");
        }
        //usersMutableLiveData=new MutableLiveData<>();

    }

    public MutableLiveData<String> getUname() {
        return uname;
    }


    public MutableLiveData<String> getPwd() {
        return pwd;
    }

    public MutableLiveData<Boolean> getSignUpSuccessfull() {
        return signUpSuccessfull;
    }

    public MutableLiveData<String> getExceptionMsg() {
        return exceptionMsg;
    }

    public MutableLiveData<Integer> getValidCredentials() {
        return validCredentials;
    }

    public MutableLiveData<Users> getUser()
    {
        if(usersMutableLiveData==null)
        {
            usersMutableLiveData=new MutableLiveData<>();
        }
        return usersMutableLiveData;
    }

    public void onLoginBtnPressed()
    {
        usersMutableLiveData=getUser();
        usersMutableLiveData.setValue(new Users(uname.getValue(),pwd.getValue()));

        if (checkUsernameAndPwd() == 1) {
            Log.i("validCredentials","1");

            try {
                mAuth.signInWithEmailAndPassword(uname.getValue(), pwd.getValue())
                        .addOnCompleteListener((task)->{
                            if (task.isSuccessful()) {
                                user = mAuth.getCurrentUser();
                                Log.d("LoginViewModel ","successfull");
                                signInSuccessfull.setValue(true);
                                //transition.goTo(HomePage.class);
                            } else {
                                //p.fprintf("Unable to Login\nError: " + task.getException());
                                signInSuccessfull.setValue(false);
                                exceptionMsg.setValue(""+task.getException().getMessage());
                                Log.e("LoginViewModel ","failed\n"+task.getException().getMessage());
                            }
                        })
                        .addOnFailureListener((exception)->{
                            exceptionMsg.setValue(exception.getMessage());
                            Log.e("LoginViewModel",exception.getMessage());
                        });
            } catch (Exception e) {
                e.printStackTrace();
                exceptionMsg.setValue(""+e.getMessage());
            }
        }
    }

    public void onSignUpBtnPressed(){
        if (checkUsernameAndPwd() == 1) {
                    mAuth.createUserWithEmailAndPassword(uname.getValue(), pwd.getValue())
                            .addOnCompleteListener((task)->{
                                if (task.isSuccessful()) {
                                    user = mAuth.getCurrentUser();
                                    //transition.goTo(HomePage.class);
                                    addHimToDatabase(user.getUid());
                                } else {
                                    //p.fprintf("Unable to signUp\nError: " + task.getException());
                                    exceptionMsg.setValue(""+task.getException().getMessage());
                                    Log.e("UserCreationFailed",""+ task.getException());
                                }
                            });

                }
    }

    int checkUsernameAndPwd() {
        if (uname.getValue().isEmpty()) {
            //p.fprintf("Username can't be empty");
            validCredentials.setValue(-1);
            return -1;
        }
        if (uname.getValue().length() < 6) {
            //p.fprintf("Username must contain atleast 6 characters");
            validCredentials.setValue(-2);
            return -2;
        }
        if (pwd.getValue().isEmpty()) {
            //p.fprintf("Password can't be empty");
            validCredentials.setValue(-3);
            return -3;
        }
        if (pwd.getValue().length() < 6) {
            //p.fprintf("Password must contain atleast 6 characters");
            validCredentials.setValue(-4);
            return -4;
        }
        validCredentials.setValue(1);
        return 1;
    }

    private void addHimToDatabase(String uid) {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference mReference=database.getReference("Users");

        String id=uid;

        HashMap<String,String> hashMap=new HashMap<>();
        //hashMap.put("uname",uname);
        hashMap.put("uid",uid);

        mReference.child(id).setValue(hashMap)
                .addOnSuccessListener((aVoid) -> {
                    signUpSuccessfull.setValue(true);
                })
                .addOnFailureListener((exception)->{
                    signUpSuccessfull.setValue(false);
                    exceptionMsg.setValue(""+exception.getMessage());

                });


    }

}
