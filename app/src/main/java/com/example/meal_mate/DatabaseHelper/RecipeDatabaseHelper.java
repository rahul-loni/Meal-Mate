package com.example.meal_mate.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.meal_mate.Model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDatabaseHelper  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "grocery_app.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_RECIPES = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GROCERIES = "groceries";
    private static final String COLUMN_WEEK = "week";

    private static final String TABLE_GROCERIES = "grocery_table";
    private static final String GROCERY_NAME = "name";

    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE recipes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "groceries TEXT, "
                + "week TEXT, "
                + "imagePath TEXT)";
        db.execSQL(CREATE_RECIPES_TABLE);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERIES);
        onCreate(db);
    }
    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("groceries", String.join(",", recipe.getGroceries()));
        values.put("week", recipe.getWeek());
        values.put("imagePath", recipe.getImagePath());

        db.insert("recipes", null, values);
        db.close();
    }
    public void updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", recipe.getName());
        values.put("groceries", String.join(",", recipe.getGroceries()));
        values.put("week", recipe.getWeek());
        values.put("imagePath", recipe.getImagePath());

        db.update("recipes", values, "id = ?", new String[]{String.valueOf(recipe.getId())});
        db.close();
    }
    public void deleteRecipe(int recipeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("recipes", "id = ?", new String[]{String.valueOf(recipeId)});
        db.close();
    }
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("recipes", null, null, null, null, null, "id DESC");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                List<String> groceries = Arrays.asList(cursor.getString(2).split(","));
                String week = cursor.getString(3);
                String imagePath = cursor.getString(4);

                recipeList.add(new Recipe(id, name, groceries, week, imagePath));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipeList;
    }
    public List<String> getAllGroceries() {
        List<String> groceries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_GROCERIES, new String[]{GROCERY_NAME}, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                groceries.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return groceries;
    }
}
