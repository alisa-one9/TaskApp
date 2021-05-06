package com.example.taskapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskapp.models.Model;

import java.util.List;

@Dao
public interface TaskDao {


    @Query("SELECT * FROM model")
    List<Model> getALL();

    @Insert
    void insert(Model model);

    @Query("SELECT*FROM model")
    LiveData<List<Model>> getAllLive();

    @Delete
    void delete(Model model);

    @Update
    void update(Model model);

    @Query("SELECT * FROM model ORDER BY name ASC")
    List<Model> sortAll();


}
