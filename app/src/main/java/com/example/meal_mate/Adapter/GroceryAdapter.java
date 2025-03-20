package com.example.meal_mate.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meal_mate.Model.Grocery;
import com.example.meal_mate.R;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {
    private List<Grocery> groceries;
    private Context context;

    public GroceryAdapter(Context context, List<Grocery> groceries) {
        this.context = context;
        this.groceries = groceries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery grocery = groceries.get(position);
        holder.name.setText(grocery.getName());
        holder.price.setText("$" + grocery.getPrice());
        holder.description.setText(grocery.getDescription());
        holder.location.setText(grocery.getLocation());
        holder.image.setImageURI(Uri.parse(grocery.getImagePath()));
    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, description, location;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.grocery_name);
            price = view.findViewById(R.id.grocery_price);
            description = view.findViewById(R.id.grocery_description);
            location = view.findViewById(R.id.grocery_location);
            image = view.findViewById(R.id.grocery_image);
        }
    }
}
