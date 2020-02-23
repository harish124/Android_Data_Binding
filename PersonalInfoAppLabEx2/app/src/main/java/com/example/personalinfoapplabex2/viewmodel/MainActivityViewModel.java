package com.example.personalinfoapplabex2.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.personalinfoapplabex2.model.User;

import java.util.ArrayList;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> name,dob,address,email,phno,gender;
    private MutableLiveData<ArrayList<String>> arrayList;
    private ArrayList<String> lang;
    private User u;


    public void init(){
       //default values
        name=new MutableLiveData<>();
        dob=new MutableLiveData<>();
        address=new MutableLiveData<>();
        email=new MutableLiveData<>();
        phno=new MutableLiveData<>();
        gender=new MutableLiveData<>();

        name.setValue("Won Bin");
        dob.setValue("1/4/2005");
        address.setValue("Jurong West,Singapore");
        email.setValue("binWon@yahoo.in");
        phno.setValue("9876543210");
        gender.setValue("Male");
        arrayList=new MutableLiveData<>();
        lang=new ArrayList<>();
        u=new User(name.getValue(),dob.getValue(),address.getValue(),email.getValue(),phno.getValue());
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<String> getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob.setValue(dob);
    }

    public MutableLiveData<String> getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address.setValue(address);
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public MutableLiveData<String> getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno.setValue(phno);
    }

    public ArrayList<String> getLang() {
        return lang;
    }

    public void setLang(ArrayList<String> lang) {
        this.lang = lang;
    }
}
