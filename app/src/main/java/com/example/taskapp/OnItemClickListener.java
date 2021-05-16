package com.example.taskapp;

import com.example.taskapp.models.Model;

public interface OnItemClickListener {

    void onClick(Model model,int position);

    void onLongClick(Model model,int position);
}
