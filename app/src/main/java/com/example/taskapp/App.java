package com.example.taskapp;

import android.app.Application;

import androidx.room.Room;


import com.example.taskapp.room.AppDatabase;

public class App extends Application {
    public static AppDatabase appDatabase;
    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room
                .databaseBuilder(this, AppDatabase.class,"dataBase")
                .allowMainThreadQueries()
                .build();
    }
}
