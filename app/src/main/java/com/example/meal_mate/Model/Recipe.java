package com.example.meal_mate.Model;

import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private List<String> groceries;
    private String week;
    private String imagePath;

    public Recipe(int id, String name, List<String> groceries, String week, String imagePath) {
        this.id = id;
        this.name = name;
        this.groceries = groceries;
        this.week = week;
        this.imagePath = imagePath;
    }
    public Recipe(String name, List<String> groceries, String week, String imagePath) {
        this.name = name;
        this.groceries = groceries;
        this.week = week;
        this.imagePath = imagePath;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<String> getGroceries() {
        return groceries;
    }
    public String getWeek() {
        return week;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGroceries(List<String> groceries) {
        this.groceries = groceries;
    }
    public void setWeek(String week) {
        this.week = week;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
