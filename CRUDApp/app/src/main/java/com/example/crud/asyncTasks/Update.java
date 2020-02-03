package com.example.crud.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.crud.databinding.ActivityMainBinding;
import com.example.crud.models.User;
import com.example.crud.my_database.UserDatabase;

import java.lang.ref.WeakReference;

public class Update extends AsyncTask<Void, Void, Void> {
    private WeakReference<Context> c;
    private int id;
    private User user;

    public Update(Context c, int id, User user) {
        this.c = new WeakReference<>(c);
        this.id = id;
        this.user=user;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        UserDatabase ud = UserDatabase.getAppDatabase(c.get());
        ud.userDAO().update(user.getName(), user.getPhno(), user.getAddr(), user.getEmail(), id);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("Update Record","Successfull");
    }
}
