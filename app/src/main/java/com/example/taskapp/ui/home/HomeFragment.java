package com.example.taskapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.models.Model;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private final String KeyData = "keyData";
    private final String ket = "key";

    private ArrayList<Model> list;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private int position;
    private boolean chekAddModel = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
            Model model = (Model) result.getSerializable("model");
            adapter.addItem(model);
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

    public void onItemClick(int position) {
        this.position = position;
        chekAddModel = true;
    }

    public void send(Model model) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("keyData", model);
        getParentFragmentManager().setFragmentResult("key", bundle);
        openForm();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortForHomeFragment:
                App.appDatabase.taskDao().sortAll();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.pop_ap_menu_for_sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}