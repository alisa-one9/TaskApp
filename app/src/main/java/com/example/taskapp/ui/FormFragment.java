package com.example.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.models.Model;

import java.io.Serializable;
import java.util.Date;


public class FormFragment extends Fragment {

    private EditText editText;
    private TextView createdAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.btnSave).setOnClickListener(v -> {
            save();
        });
        getParentFragmentManager().setFragmentResultListener("key", getViewLifecycleOwner(), (requestKey, result) -> {
            Serializable model = result.getSerializable("keyData");
        });
    }

    private void save() {
        String text = editText.getText().toString();
        if (text != null) {
            Model model = new Model(text, new Date());
            App.appDatabase.taskDao().insert(model);
            Bundle bundle = new Bundle();
            bundle.putSerializable("model", model);
            getParentFragmentManager().setFragmentResult("rk_task", bundle);
            close();
        }
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}