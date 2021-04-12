package com.example.taskapp.ui;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp.R;


public class ProfileFragment extends Fragment {

    private TextView nameFromGallery;
    private ImageView imageFromGallery;
    private ActivityResultLauncher<String> content;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameFromGallery = view.findViewById(R.id.name);
        imageFromGallery = view.findViewById(R.id.imageGallery);


        imageFromGallery.setOnClickListener(v -> {
            ProfileFragment.this.content.launch("image/*");
        });
        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageFromGallery.setImageURI(result);
            }
        });


//        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//        navController.navigate(R.id.profileFragment);



    }





}