package com.example.meal_mate.Model;

public class Grocery {
    private int id;
    private String name;
    private double price;
    private String description;
    private String imagePath;
    private String location;

    public Grocery(int id, String name, double price, String description, String imagePath, String location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
        this.location = location;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public String getLocation() { return location; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setLocation(String location) { this.location = location; }
}
