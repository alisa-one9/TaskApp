package com.example.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp.R;


public class FormFragment extends Fragment {

    private EditText editText;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText =view.findViewById(R.id.editText);
        view.findViewById(R.id.btnSave).setOnClickListener(v -> {
            save();
        });
        getParentFragmentManager().setFragmentResultListener("ket", getViewLifecycleOwner(), (requestKey, result) -> {
            String title = result.getString("key");
            editText.setText(title);
        });
    }

    private void save() {
        String text = editText.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("title",text);
        getParentFragmentManager().setFragmentResult("rk_task",bundle);
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}