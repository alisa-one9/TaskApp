package com.example.taskapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.R;

public class HomeFragment extends Fragment implements TaskAdapter.onClick {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fab).setOnClickListener(v -> {
            openForm();
        });
        initList(view);
        getParentFragmentManager().setFragmentResultListener("rk_task", getViewLifecycleOwner(), (requestKey, result) -> {
            String title = result.getString("title");
            adapter.addItem(title);

        });

    }

    private void initList(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);


    }

    public void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }

    @Override
    public void send(String string) {
        Bundle bundle = new Bundle();
        bundle.putString("key",string);
        getParentFragmentManager().setFragmentResult("ket",bundle);
        openForm();
    }
}