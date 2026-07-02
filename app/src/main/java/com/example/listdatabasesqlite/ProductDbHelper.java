package com.example.listdatabasesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite database helper for the products catalog.
 *
 * <p>Creates the {@code products} table on first install and seeds it with
 * sample data. Upgrading the database version drops and recreates the table.</p>
 */
public class ProductDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_NAME = "products";
    static final String COL_ID = "id";
    static final String COL_NAME = "name";
    static final String COL_DESCRIPTION = "description";
    static final String COL_SELLER = "seller";
    static final String COL_PRICE = "price";
    static final String COL_IMAGE_RES_ID = "image_res_id";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT NOT NULL, " +
            COL_DESCRIPTION + " TEXT, " +
            COL_SELLER + " TEXT, " +
            COL_PRICE + " REAL, " +
            COL_IMAGE_RES_ID + " INTEGER);";

    /**
     * @param context the application or activity context used to open the database
     */
    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called once when the database is first created.
     * Creates the products table and inserts data.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        seedProducts(db);
    }

    /**
     * Called when the database version is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserts a fixed set of sample products into the database
     */
    private void seedProducts(SQLiteDatabase db) {
        int placeholder = R.drawable.ic_launcher_background;

        insert(db, "Laptop Pro 15",
                "High-performance laptop with 15-inch display, 16GB RAM, and 512GB SSD.",
                "TechWorld", 1299.99, placeholder);

        insert(db, "Wireless Headphones",
                "Over-ear noise-cancelling headphones with 30-hour battery life.",
                "SoundHouse", 249.99, placeholder);

        insert(db, "Running Shoes",
                "Lightweight running shoes with responsive cushioning for all-terrain use.",
                "StrideCo", 89.95, placeholder);

        insert(db, "Coffee Maker",
                "12-cup programmable coffee maker with built-in grinder and thermal carafe.",
                "BrewMaster", 74.99, placeholder);

        insert(db, "Backpack 40L",
                "Durable 40-liter hiking backpack with rain cover and ergonomic support.",
                "TrailGear", 59.99, placeholder);

        insert(db, "Smartwatch Series X",
                "Fitness smartwatch with heart rate monitor, GPS, and 7-day battery life.",
                "WristTech", 199.99, placeholder);
    }

    /**
     * Inserts a single product row into the given database.
     *
     * @param db          the writable database
     * @param name        the product name
     * @param description the product description
     * @param seller      the seller name
     * @param price       the price in USD
     * @param imageResId  the drawable resource ID for the product image
     */
    private void insert(SQLiteDatabase db, String name, String description,
                        String seller, double price, int imageResId) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_SELLER, seller);
        values.put(COL_PRICE, price);
        values.put(COL_IMAGE_RES_ID, imageResId);
        db.insert(TABLE_NAME, null, values);
    }

    /**
     * Queries all products from the database, sorted alphabetically by name.
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COL_NAME + " ASC");

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION));
                String seller = cursor.getString(cursor.getColumnIndexOrThrow(COL_SELLER));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_PRICE));
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_IMAGE_RES_ID));
                products.add(new Product(id, name, description, seller, price, imageResId));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return products;
    }
}
