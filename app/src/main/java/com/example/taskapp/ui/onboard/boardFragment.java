package com.example.taskapp.ui.onboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.taskapp.OnItemClickListener;
import com.example.taskapp.R;
import com.example.taskapp.ui.home.TaskAdapter;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;



public class boardFragment extends Fragment implements TaskAdapter.onClick {
    private ViewPager2 viewPager;
    private SpringDotsIndicator springDotsIndicator;
    private Button btnSkip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         viewPager = view.findViewById(R.id.viewPager);
         springDotsIndicator = view.findViewById(R.id.dots);
        BoardAdapter adapter = new BoardAdapter(this::send);
        viewPager.setAdapter(adapter);
        springDotsIndicator.setViewPager2(viewPager);
        btnSkip =view.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            public void onClick(int position) {
                close();
            }
        });
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }
    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

    @Override
    public void send(String string) {
        close();
    }
}