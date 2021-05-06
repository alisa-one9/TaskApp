package com.example.taskapp.ui;


import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taskapp.utils.Preferences;
import com.example.taskapp.R;


public class ProfileFragment extends Fragment {
    private ImageView imageFromGallery;
    private EditText aboutCompany;
    private EditText telNumber;
    private EditText nameProfile;

    private ActivityResultLauncher<String> content;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Preferences.getInstance(getContext()).getTxt() != null) {
            nameProfile.setText(Preferences.getInstance().getTxt());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageFromGallery = view.findViewById(R.id.imageGallery);
        aboutCompany = view.findViewById(R.id.aboutCompany);
        telNumber = view.findViewById(R.id.telNumber);
        nameProfile = view.findViewById(R.id.nameProfile);

        imageFromGallery.setOnClickListener(v -> {
            ProfileFragment.this.content.launch("image/*");
        });
        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                imageFromGallery.setImageURI(result);
            }
        });
        nameProfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Preferences.saveNameProfile(s.toString());
                Log.e("TAG", "afterTextChanged: " + s.toString());
            }
        });


//       public void closeProfile() {
//       NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
//        navController.navigateUp();}
    }


}