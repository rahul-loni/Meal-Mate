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
    private static final String DATABASE_NAME = "groceryDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "grocery";

    public GroceryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, price REAL, description TEXT, image TEXT, location TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert Grocery
    public long addGrocery(Grocery grocery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", grocery.getName());
        values.put("price", grocery.getPrice());
        values.put("description", grocery.getDescription());
        values.put("image", grocery.getImageUri());
        values.put("location", grocery.getLocation());

        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    // Fetch All Groceries
    public List<Grocery> getAllGroceries() {
        List<Grocery> groceryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(cursor.getInt(0));
                grocery.setName(cursor.getString(1));
                grocery.setPrice(cursor.getDouble(2));
                grocery.setDescription(cursor.getString(3));
                grocery.setImageUri(cursor.getString(4));
                grocery.setLocation(cursor.getString(5));
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
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}

