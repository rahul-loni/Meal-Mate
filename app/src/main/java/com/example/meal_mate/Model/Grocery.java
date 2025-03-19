package com.example.meal_mate.Model;

public class Grocery {
    private int id;
    private String name, description, imageUri, location;
    private double price;

    public Grocery() {}

    public Grocery(int id, String name, double price, String description, String imageUri, String location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUri = imageUri;
        this.location = location;
    }

    // Getter and Setter Methods
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUri() {
        return imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}


