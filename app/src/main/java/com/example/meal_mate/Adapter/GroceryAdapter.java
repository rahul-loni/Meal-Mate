package com.example.meal_mate.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.meal_mate.DatabaseHelper.GroceryDatabaseHelper;
import com.example.meal_mate.Model.Grocery;
import com.example.meal_mate.R;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.ViewHolder> {
    private Context context;
    private List<Grocery> groceryList;
    private GroceryDatabaseHelper dbHelper;

    public GroceryAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
        this.dbHelper = new GroceryDatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grocery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Grocery grocery = groceryList.get(position);
        holder.name.setText(grocery.getName());
        holder.price.setText("$" + grocery.getPrice());
        holder.description.setText(grocery.getDescription());
        holder.location.setText(grocery.getLocation());

        // Load Image Using Glide
        if (!grocery.getImageUri().isEmpty()) {
            Glide.with(context).load(Uri.parse(grocery.getImageUri())).into(holder.image);
        }

        holder.btnDelete.setOnClickListener(v -> {
            dbHelper.deleteGrocery(grocery.getId());
            groceryList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, description, location;
        ImageView image;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.groceryName);
            price = itemView.findViewById(R.id.groceryPrice);
            description = itemView.findViewById(R.id.groceryDescription);
            location = itemView.findViewById(R.id.groceryLocation);
            image = itemView.findViewById(R.id.groceryImage);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
