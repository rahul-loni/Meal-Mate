package com.example.meal_mate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meal_mate.DatabaseHelper.GroceryDatabaseHelper;

public class GroceryFormActivity extends AppCompatActivity {
    private EditText editName, editPrice, editDescription, editLocation;
    private ImageView groceryImage;
    private Button btnSave, btnSelectImage;
    private GroceryDatabaseHelper dbHelper;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        editName = findViewById(R.id.editName);
        editPrice = findViewById(R.id.editPrice);
        editDescription = findViewById(R.id.editDescription);
        editLocation = findViewById(R.id.editLocation);
        groceryImage = findViewById(R.id.groceryImage);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        dbHelper = new GroceryDatabaseHelper(this);

        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        groceryImage.setImageURI(imageUri);
                    }
                }
        );

        btnSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> saveGrocery());
    }

    private void saveGrocery() {
        Grocery grocery = new Grocery();
        grocery.setName(editName.getText().toString());
        grocery.setPrice(Double.parseDouble(editPrice.getText().toString()));
        grocery.setDescription(editDescription.getText().toString());
        grocery.setLocation(editLocation.getText().toString());
        grocery.setImageUri(imageUri != null ? imageUri.toString() : "");

        dbHelper.addGrocery(grocery);
        finish();
    }
}
