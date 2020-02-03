<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="viewModel"
            type="com.example.crud.view_model.MainActivityViewModel" />
    </data>
    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        style="@style/Theme.AppCompat.NoActionBar"
        tools:context=".MainActivity">

        <TextView
            android:id="@+id/title"
            android:layout_margin="20dp"
            android:layout_centerHorizontal="true"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Welcome to CRUD" />

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/tl1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@+id/title">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/name"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="Name"
                android:text="@={viewModel.name,default=Harish}" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/tl2"
            android:layout_below="@+id/tl1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/address"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@={viewModel.addr}"
                android:hint="Address" />
        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/tl3"
            android:layou