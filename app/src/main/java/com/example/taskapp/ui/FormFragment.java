package com.example.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentFormBinding;
import com.example.taskapp.models.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.Date;


public class FormFragment extends Fragment {
    private NavController navController;
    private FragmentFormBinding binding;
    private EditText editText;
    private TextView createdAt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentFormBinding.inflate(inflater, container, false);
        sendFromFormToHome();
        return binding.getRoot();
    }

    private void sendFromFormToHome() {
        binding.btnSave.setOnClickListener(v -> {
            String text = editText.getText().toString();
            Model model = new Model(text, new Date());
            Bundle bundle = new Bundle();
            bundle.putSerializable("key_model_from_Form", model);
            getParentFragmentManager().setFragmentResult("key_fragment_from_Form", bundle);
            saveToFireStore(model);
            navController.navigateUp();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        getParentFragmentManager().setFragmentResultListener("key_fragment_from_Home", getViewLifecycleOwner(), (requestKey, result) -> {
            Serializable model = result.getSerializable("key_model_from_Home");
        });
    }

    private void saveToFireStore(Model myTask) {
        FirebaseFirestore.getInstance().collection("notes").add(myTask).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    if (isAdded()) Toast.makeText(requireActivity(), "Успешно сохранено в Firestore!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}