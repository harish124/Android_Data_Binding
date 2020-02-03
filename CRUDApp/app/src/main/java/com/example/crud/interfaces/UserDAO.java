package com.example.crud.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.crud.models.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    Long insert(User u);

    @Query("select * from `User`")
    List<User> getAllUsers();

    @Query("select * from `User` where `uid` =:id ")
    User getUser(int id);

    @Query("update `User` set `name` =:name , `phno`=:phno,`email`=:email" +
            ",`addr`=:addr where `uid` =:id")
    void update(String name,String phno,String addr,String email,int id);

    @Query("delete from `User` where `uid`=:id")
    void delete(int id);

    @Delete
    void delete(User u);
}
