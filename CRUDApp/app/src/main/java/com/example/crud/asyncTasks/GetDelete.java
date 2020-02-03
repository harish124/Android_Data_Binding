package com.example.crud.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.crud.databinding.ActivityMainBinding;
import com.example.crud.my_database.UserDatabase;
import com.example.crud.print.Print;
import com.example.crud.view_model.MainActivityViewModel;

import java.lang.ref.WeakReference;

public class GetDelete extends AsyncTask<Void,Void,Void> {
    private WeakReference<Context> c;
    private int id;
    MainActivityViewModel vm;
    private Print p;

    public GetDelete(Context c, int id,MainActivityViewModel vm) {
        //Toast.makeText(c,"Reached constructor",Toast.LENGTH_SHORT).show();
        this.c = new WeakReference<>(c);
        this.id = id;
        this.vm=vm;
        p=new Print(c);
    }
    @Override
    protected Void doInBackground(Void... voids) {
        UserDatabase ud = UserDatabase.getAppDatabase(c.get());
        ud.userDAO().delete(id);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("Deletion","Successfull");
        p.sprintf("User Deleted Successfully");
        vm.getAllUsers();
    }
}
