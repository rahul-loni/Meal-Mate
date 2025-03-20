package com.example.meal_mate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.meal_mate.Adapter.GroceryAdapter;
import com.example.meal_mate.DatabaseHelper.GroceryDatabaseHelper;
import com.example.meal_mate.Model.Grocery;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GroceryFragment extends Fragment {
    private RecyclerView recyclerView;
    private GroceryAdapter adapter;
    private GroceryDatabaseHelper databaseHelper;
    private FloatingActionButton btnAddGrocery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grocery, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        btnAddGrocery = view.findViewById(R.id.fab);
        databaseHelper = new GroceryDatabaseHelper(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAddGrocery.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddGrocery.class);
            startActivity(intent);
        });

        loadGroceries();

        return view;
    }

    private void loadGroceries() {
        List<Grocery> groceryList = databaseHelper.getAllGroceries();
        adapter = new GroceryAdapter(getContext(), groceryList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadGroceries();  // Refresh the list when returning to the fragment
    }
}
