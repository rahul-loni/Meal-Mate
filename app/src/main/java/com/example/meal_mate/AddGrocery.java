package com.example.meal_mate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meal_mate.DatabaseHelper.GroceryDatabaseHelper;
import com.example.meal_mate.Model.Grocery;

import java.io.IOException;

public class AddGrocery extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_LOCATION_REQUEST = 2;

    private EditText etName, etPrice, etDescription;
    private ImageView ivGroceryImage;
    private TextView tvLocation;
    private Button btnChooseImage, btnChooseLocation, btnSave;
    private Uri imageUri;
    private String selectedLocation;
    private GroceryDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        ivGroceryImage = findViewById(R.id.ivGroceryImage);
        tvLocation = findViewById(R.id.tvLocation);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnChooseLocation = findViewById(R.id.btnChooseLocation);
        btnSave = findViewById(R.id.btnSave);
        databaseHelper = new GroceryDatabaseHelper(this);

        btnChooseImage.setOnClickListener(v -> openImageChooser());
        btnChooseLocation.setOnClickListener(v -> openMap());
        btnSave.setOnClickListener(v -> saveGrocery());


//        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                        imageUri = result.getData().getData();
//                        groceryImage.setImageURI(imageUri);
//                    }
//                }
//        );
//
//        btnSelectImage.setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_PICK);
//            intent.setType("image/*");
//            imagePickerLauncher.launch(intent);
//        });
//
//        btnSave.setOnClickListener(v -> saveGrocery());
//    }
//
//    private void saveGrocery() {
//        Grocery grocery = new Grocery();
//        grocery.setName(editName.getText().toString());
//        grocery.setPrice(Double.parseDouble(editPrice.getText().toString()));
//        grocery.setDescription(editDescription.getText().toString());
//        grocery.setLocation(editLocation.getText().toString());
//        grocery.setImageUri(imageUri != null ? imageUri.toString() : "");
//
//        dbHelper.addGrocery(grocery);
//        finish();
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void openMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivityForResult(intent, PICK_LOCATION_REQUEST);
    }

    private void saveGrocery() {
        String name = etName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (name.isEmpty() || price.isEmpty() || description.isEmpty() || imageUri == null || selectedLocation == null) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", Double.parseDouble(price));
        values.put("description", description);
        values.put("image", imageUri.toString());
        values.put("location", selectedLocation);

        db.insert("grocery", null, values);
        db.close();

        Toast.makeText(this, "Grocery added!", Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ivGroceryImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_LOCATION_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedLocation = data.getStringExtra("selected_location");
            tvLocation.setText(selectedLocation);
        }
    }
}
