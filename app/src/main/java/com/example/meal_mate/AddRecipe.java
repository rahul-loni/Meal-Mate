package com.example.meal_mate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meal_mate.Adapter.GroceryAdapter;
import com.example.meal_mate.DatabaseHelper.GroceryDatabaseHelper;
import com.example.meal_mate.DatabaseHelper.RecipeDatabaseHelper;
import com.example.meal_mate.Model.Grocery;
import com.example.meal_mate.Model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddRecipe extends AppCompatActivity {

    private EditText editRecipeName;
    private Spinner spinnerGroceries, spinnerWeek;
    private Button btnSave, btnSelectImage;
//    private TextView txtSelectedGroceries;
    private RecipeDatabaseHelper databaseHelper;
    private Uri selectedImageUri;
    private ImageView imageView;
    private GroceryDatabaseHelper groceryDatabaseHelper;
    private List<Grocery> groceryList;
    private String imagePath = "";
    private int recipeId = -1;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe          );

        editRecipeName = findViewById(R.id.editRecipeName);
        spinnerGroceries = findViewById(R.id.spinnerGroceries);
        spinnerWeek = findViewById(R.id.spinnerWeek);
        imageView = findViewById(R.id.imageViewRecipe);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImage = findViewById(R.id.btnSelectImage);
//        txtSelectedGroceries = findViewById(R.id.spinnerGroceries);

        groceryDatabaseHelper = new GroceryDatabaseHelper(this);
        databaseHelper = new RecipeDatabaseHelper(this);

        loadGroceryDropdown(); // Load grocery names in dropdown
        loadWeekDropdown(); // Load week options

        btnSave.setOnClickListener(view -> {
            String name = editRecipeName.getText().toString();
            String selectedWeek = spinnerWeek.getSelectedItem().toString();
            String selectedGrocery = spinnerGroceries.getSelectedItem().toString(); // Get selected grocery name

            if (!name.isEmpty() && !selectedGrocery.isEmpty()) {
                List<String> groceries = new ArrayList<>();
                groceries.add(selectedGrocery);

                Recipe newRecipe = new Recipe(name, groceries, selectedWeek, imagePath);

                if (recipeId == -1) {
                    databaseHelper.addRecipe(newRecipe);
                    Toast.makeText(this, "Recipe added!", Toast.LENGTH_SHORT).show();
                } else {
                    newRecipe.setId(recipeId);
                    databaseHelper.updateRecipe(newRecipe);
                    Toast.makeText(this, "Recipe updated!", Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                Toast.makeText(this, "Enter recipe details and select a grocery", Toast.LENGTH_SHORT).show();
            }
        });

        btnSelectImage.setOnClickListener(v -> openImageChooser());
    }
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handle the result of the image picker (onActivityResult)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData(); // Get URI of the selected image
            imageView.setImageURI(selectedImageUri); // Set image in the ImageView
        }
    }

    private void loadGroceryDropdown() {
        groceryList = groceryDatabaseHelper.getAllGroceries();
        GroceryAdapter adapter = new GroceryAdapter(this, groceryList);
        spinnerGroceries.setAdapter((SpinnerAdapter) adapter);
    }

    private void loadWeekDropdown() {
        List<String> weeks = Arrays.asList("Week 1", "Week 2", "Week 3", "Week 4");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weeks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeek.setAdapter(adapter);
    }
}
