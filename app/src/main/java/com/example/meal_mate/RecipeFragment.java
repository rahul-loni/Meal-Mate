package com.example.meal_mate;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meal_mate.Adapter.RecipeAdapter;
import com.example.meal_mate.Model.Recipe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        // Initialize RecyclerView and FloatingActionButton
        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);

        // Set up the layout manager for RecyclerView
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the recipe list and adapter
        recipeList = new ArrayList<>();

        // Fetch recipe data (replace with your database fetching logic)
        fetchRecipeData();

        // Set the adapter to the RecyclerView
        recipeAdapter = new RecipeAdapter(getActivity(), recipeList);
        recyclerView.setAdapter(recipeAdapter);

        // Set up FAB onClickListener to add a new recipe
        fab.setOnClickListener(view1 -> {
            // Code to navigate to AddRecipeActivity or open a dialog
            Intent intent = new Intent(getActivity(), AddRecipe.class);
            startActivity(intent);
        });

        return view;
    }

    // Dummy method to simulate fetching data from a database
    private void fetchRecipeData() {
        // Example: Add some dummy data for demonstration
        List<String> groceries = new ArrayList<>();
        groceries.add("Rice");
        groceries.add("Beans");

        recipeList.add(new Recipe("Recipe 1", groceries, "Week 1", "https://example.com/image1.jpg"));
        recipeList.add(new Recipe("Recipe 2", groceries, "Week 2", "https://example.com/image2.jpg"));
    }
}
