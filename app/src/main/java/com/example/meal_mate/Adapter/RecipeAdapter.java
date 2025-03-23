package com.example.meal_mate.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.meal_mate.Model.Recipe;
import com.example.meal_mate.R;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;

    // Constructor for the adapter
    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the custom recipe list item layout
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(itemView);
    }

    // Bind the data to the views (called by the layout manager)
    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        // Set recipe name, week, and groceries
        holder.recipeName.setText(recipe.getName());
        holder.recipeWeek.setText("Week: " + recipe.getWeek());

        // Set grocery items (if multiple, show as comma-separated string)
        holder.recipeGroceries.setText("Groceries: " + TextUtils.join(", ", recipe.getGroceries()));

        // Set image (if URI is available)
        if (recipe.getImagePath() != null && !recipe.getImagePath().isEmpty()) {
            Glide.with(context)
                    .load(recipe.getImagePath()) // Load the image URI
                    .into(holder.recipeImage);
        } else {
            holder.recipeImage.setImageResource(R.drawable.upload); // Placeholder image
        }
    }

    // Return the size of the data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // ViewHolder class to hold the views for each recipe item
    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView recipeName, recipeWeek, recipeGroceries;
        public ImageView recipeImage;

        public RecipeViewHolder(View view) {
            super(view);
            recipeName = view.findViewById(R.id.recipeName);
            recipeWeek = view.findViewById(R.id.recipeWeek);
            recipeGroceries = view.findViewById(R.id.recipeGroceries);
            recipeImage = view.findViewById(R.id.recipeImage);
        }
    }
}
