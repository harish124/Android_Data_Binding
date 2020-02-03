package com.example.crud.my_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.crud.interfaces.UserDAO;
import com.example.crud.models.User;


@Database(entities = {User.class},version=1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    public static UserDatabase instance;

    public static UserDatabase getAppDatabase(Context ctx){
        if(instance==null){
            instance= Room.databaseBuilder(ctx,UserDatabase.class,"CashItUsers").build();
        }

        return instance;
    }

    public static void destroyInstance() {
        instance= null;
    }
}
