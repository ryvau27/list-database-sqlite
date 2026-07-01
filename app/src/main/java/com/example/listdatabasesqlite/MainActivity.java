package com.example.listdatabasesqlite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_SELECTED_PRODUCTS = "selected_products";
    private static final int MIN_SELECTION = 3;

    private ProductAdapter adapter;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = findViewById(R.id.btnNext);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Product> products = new ProductDbHelper(this).getAllProducts();
        adapter = new ProductAdapter(products, this::onSelectionChanged);
        recyclerView.setAdapter(adapter);

        btnNext.setOnClickListener(v -> {
            ArrayList<Product> selected = new ArrayList<>(adapter.getSelectedProducts());
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(EXTRA_SELECTED_PRODUCTS, selected);
            startActivity(intent);
        });
    }

    private void onSelectionChanged(int selectedCount) {
        btnNext.setEnabled(selectedCount >= MIN_SELECTION);
    }
}
