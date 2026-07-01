package com.example.listdatabasesqlite;

import java.io.Serializable;

public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private String seller;
    private double price;
    private int imageResId;

    public Product(int id, String name, String description, String seller, double price, int imageResId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSeller() { return seller; }
    public double getPrice() { return price; }
    public int getImageResId() { return imageResId; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSeller(String seller) { this.seller = seller; }
    public void setPrice(double price) { this.price = price; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
}
