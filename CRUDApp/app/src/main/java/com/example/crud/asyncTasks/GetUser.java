package com.example.crud.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.crud.databinding.ActivityMainBinding;
import com.example.crud.models.User;
import com.example.crud.my_database.UserDatabase;

import java.lang.ref.WeakReference;

public class GetUser extends AsyncTask<Void, Void, User> {
    private WeakReference<Context> c;
    private int id;
    ActivityMainBinding binding;

    public GetUser(Context c, int id,ActivityMainBinding binding) {
        //Toast.makeText(c,"Reached constructor",Toast.LENGTH_SHORT).show();
        this.c = new WeakReference<>(c);
        this.id = id;
        this.binding=binding;
    }

    @Override
    protected User doInBackground(Void... voids) {
        UserDatabase ud = UserDatabase.getAppDatabase(c.get());
        User u = ud.userDAO().getUser(id);
//        Toast.makeText(c.get(),"Reading in background\nu = "+u,Toast.LENGTH_SHORT).show();
        return u;
    }

    @Override
    protected void onPostExecute(User u) {
        super.onPostExecute(u);

        if(u==null){
            //Toast.makeText(c.get(),"user is null",Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(c.get(),"user is not null\n"+u.getUid(),Toast.LENGTH_SHORT).show();
        Log.d("Username",u.getName());
        Log.d("Addr",u.getAddr());
        Log.d("Email",u.getEmail());
        Log.d("UID",""+u.getUid());

        binding.name.setText(u.getName());
        binding.address.setText(u.getAddr());
        binding.email.setText(u.getEmail());
        binding.phno.setText(u.getPhno());

//            binding.id.setText(u.getUid());
    }
}
