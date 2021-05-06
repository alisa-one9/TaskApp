package com.example.taskapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.taskapp.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Model implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private Date createdAt;

    public Model(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
