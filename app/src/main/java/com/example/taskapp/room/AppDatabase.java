package com.example.taskapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskapp.models.Model;

@Database(entities ={Model.class}, version =1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
