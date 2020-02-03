package com.example.crud.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.crud.models.User;
import com.example.crud.my_database.UserDatabase;

import java.lang.ref.WeakReference;

public class AddUser extends AsyncTask<Void, Void, Void> {
    private User u;
    private WeakReference<Context> c;

    public AddUser(Context c, User u) {
        this.c = new WeakReference<>(c);
        this.u = u;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        UserDatabase ud = UserDatabase.getAppDatabase(c.get());
        ud.userDAO().insert(u);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("UserAddition","User added successfully!");

        //((Activity) c.get()).finish();
    }
}
