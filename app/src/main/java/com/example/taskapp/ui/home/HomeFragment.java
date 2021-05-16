package com.example.taskapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.OnItemClickListener;
import com.example.taskapp.R;
import com.example.taskapp.databinding.FragmentHomeBinding;
import com.example.taskapp.models.Model;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemClickListener {


    private FragmentHomeBinding binding;
    private ArrayList<Model> modelList;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private NavController navController;
    private int position;
    private boolean chekAddModel = false;
    private Model model;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TaskAdapter(HomeFragment.this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        navController = NavHostFragment.findNavController(this);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.recyclerView.setAdapter(adapter);
        binding.fab.setOnClickListener(v -> {
            openForm();
        });
        ;
        getFromFormToHome();
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        App.appDatabase.taskDao().getAllLive().observe(getViewLifecycleOwner(), new Observer<List<Model>>() {
            @Override
            public void onChanged(List<Model> models) {
                adapter.addApdateList(models);
            }
        });

    }

    private void getFromFormToHome() {
        getParentFragmentManager().setFragmentResultListener("key_fragment_from_Form",
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Model newmodel = (Model) result.getSerializable("key_model_from_Form");
                        if (newmodel != null) {
                            App.appDatabase.taskDao().insert(newmodel);
                        } else {
                            App.appDatabase.taskDao().update(newmodel);
                            FirebaseFirestore.getInstance().collection("notes")
                                    .add(model)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(requireContext(), "Сохранено в БД ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(requireContext(), "Не сохраенно в БД" + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
    }

    public void openForm() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.formFragment);
    }

    public void sendFromHomeToForm() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key_model_from_Home", model);
        getParentFragmentManager().setFragmentResult("key_fragment_from_Home", bundle);
        navController.navigate(R.id.action_navigation_home_to_formFragment);
    }

    //  @Override
    //  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    //      switch (item.getItemId()) {
    //          case R.id.sortForHomeFragment:
    //             App.appDatabase.taskDao().sortAll();
    //             adapter.notifyDataSetChanged();
    //          case R.id.textTitle:
    //              App.appDatabase.taskDao().update(model );
    //             adapter.notifyDataSetChanged();
    //              return true;
    //      }
    //     return super.onOptionsItemSelected(item);
    //  }

    //   @Override
    //   public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    //       inflater.inflate(R.menu.pop_ap_menu_for_sort, menu);
    //       super.onCreateOptionsMenu(menu, inflater);
    //   }

    @Override
    public void onClick(Model model, int position) {
        this.model = model;
        this.position = position;

    }

    @Override
    public void onLongClick(Model model, int position) {
        this.model = model;
        this.position = position;


    }
}