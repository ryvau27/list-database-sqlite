package com.example.listdatabasesqlite;

import java.io.Serializable;

/**
 * Represents a product stored in the SQLite database.
 */
public class Product implements Serializable {

    private int id;
    private String name;
    private String description;
    private String seller;
    private double price;
    /** Drawable resource ID used to display the product image. */
    private int imageResId;

    /**
     * Constructs a Product.
     *
     * @param id          the database primary key
     * @param name        the display name of the product
     * @param description a short description of the product
     * @param seller      the name of the seller
     * @param price       the price in USD
     * @param imageResId  a drawable resource ID for the product image
     */
    public Product(int id, String name, String description, String seller, double price, int imageResId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.imageResId = imageResId;
    }

    /** @return the database primary key */
    public int getId() { return id; }

    /** @return the display name of the product */
    public String getName() { return name; }

    /** @return a short description of the product */
    public String getDescription() { return description; }

    /** @return the name of the seller */
    public String getSeller() { return seller; }

    /** @return the price in USD */
    public double getPrice() { return price; }

    /** @return the drawable resource ID for the product image */
    public int getImageResId() { return imageResId; }

    /** @param id the database primary key */
    public void setId(int id) { this.id = id; }

    /** @param name the display name of the product */
    public void setName(String name) { this.name = name; }

    /** @param description a short description of the product */
    public void setDescription(String description) { this.description = description; }

    /** @param seller the name of the seller */
    public void setSeller(String seller) { this.seller = seller; }

    /** @param price the price in USD */
    public void setPrice(double price) { this.price = price; }

    /** @param imageResId a drawable resource ID for the product image */
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }
}
