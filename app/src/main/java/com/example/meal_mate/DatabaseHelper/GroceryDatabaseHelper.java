package com.example.meal_mate.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.meal_mate.Model.Grocery;

import java.util.ArrayList;
import java.util.List;

public class GroceryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "grocery_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_GROCERY = "grocery";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_LOCATION = "location";

    public GroceryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_GROCERY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_LOCATION + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERY);
        onCreate(db);
    }

    // Insert Grocery
    public void addGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, grocery.getName());
        values.put(COLUMN_PRICE, grocery.getPrice());
        values.put(COLUMN_DESCRIPTION, grocery.getDescription());
        values.put(COLUMN_IMAGE, grocery.getImagePath());
        values.put(COLUMN_LOCATION, grocery.getLocation());
        db.insert(TABLE_GROCERY, null, values);
        db.close();
    }

    // Fetch All Groceries
    public List<Grocery> getAllGroceries() {
        List<Grocery> groceryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_GROCERY, null);

        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                groceryList.add(grocery);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return groceryList;
    }

    // Delete Grocery
    public void deleteGrocery(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROCERY, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}

