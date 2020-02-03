package com.example.crud.asyncTasks;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crud.R;
import com.example.crud.adapters.MyRAdapter;
import com.example.crud.models.User;
import com.example.crud.my_database.UserDatabase;
import com.example.crud.view_model.MainActivityViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public  class GetUsers extends AsyncTask<Void, Void, List<User>> {
    private WeakReference<Context> c;
    private MainActivityViewModel vm;
    public GetUsers(Context c, MainActivityViewModel vm)
    {
        this.c = new WeakReference<>(c);
        this.vm=vm;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        UserDatabase ud = UserDatabase.getAppDatabase(c.get());
        return ud.userDAO().getAllUsers();
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
        Log.d("GetUsers",""+users.size());
        RecyclerView rv = ((Activity) c.get()).findViewById(R.id.recyclerView);

        MyRAdapter ua = new MyRAdapter(c.get(), (ArrayList<User>)users,vm);

        initRecyclerView(rv);
        rv.setAdapter(ua);
    }


    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager lm=new LinearLayoutManager(c.get());
        lm.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.addItemDecoration(new DividerItemDecoration(c.get(), DividerItemDecoration.VERTICAL));
    }
}
