package com.example.crud.view_model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.crud.asyncTasks.AddUser;
import com.example.crud.asyncTasks.GetUsers;
import com.example.crud.models.User;
import com.example.crud.my_database.UserDatabase;
import com.example.crud.print.Print;

import java.lang.ref.WeakReference;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<String> name,phno,addr,email,uid;
    private User user;
    private Context ctx;
    private Print p;
    private MainActivityViewModel vm;

    public void init(Context context,MainActivityViewModel vm){
        name=new MutableLiveData<>();
        phno=new MutableLiveData<>();
        addr=new MutableLiveData<>();
        email=new MutableLiveData<>();
        uid=new MutableLiveData<>();
        user=new User();
        ctx=context;
        p=new Print(ctx);
        this.vm=vm;

        name.setValue("");
        phno.setValue("");
        addr.setValue("");
        email.setValue("");
    }

    public MainActivityViewModel getVm() {
        return vm;
    }

    public MutableLiveData<String> getUid() {
        return uid;
    }

    public void setUid(MutableLiveData<String> uid) {
        this.uid = uid;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<String> getPhno() {
        return phno;
    }

    public void setPhno(MutableLiveData<String> phno) {
        this.phno = phno;
    }

    public MutableLiveData<String> getAddr() {
        return addr;
    }

    public void setAddr(MutableLiveData<String> addr) {
        this.addr = addr;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public void setEmail(MutableLiveData<String> email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        addr.setValue(""+user.getAddr());
        email.setValue(user.getEmail());
        phno.setValue(user.getPhno());
        name.setValue(user.getName());

    }

    public void onInsertBtnPressed(){
        user.setAddr(addr.getValue());
        user.setEmail(email.getValue());
        user.setName(name.getValue());
        user.setPhno(phno.getValue());

        Log.d("Username",user.getName());
        Log.d("Addr",user.getAddr());
        Log.d("Email",user.getEmail());
        Log.d("UID",""+user.getUid());

        new AddUser(ctx,user).execute();
        getAllUsers();
        p.sprintf("Insertion done Successfully");
    }

    public void getAllUsers(){
        //p.sprintf("ReadAllUsersBtnPressed");
        new GetUsers(ctx,vm).execute();
    }



    
}
