package com.example.listdatabasesqlite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Displays the products selected in MainActivity and provides a button
 * to send their details to a predefined email address.
 */
public class SecondActivity extends AppCompatActivity {

    private SelectedProductAdapter adapter;

    /**
     * Registered before onCreate.
     * Called when the user returns from the mail client.
     */
    private final ActivityResultLauncher<Intent> emailLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                Toast.makeText(this, R.string.toast_products_sent, Toast.LENGTH_LONG).show();
                adapter.clearAll();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ArrayList<Product> selected = (ArrayList<Product>)
                getIntent().getSerializableExtra(MainActivity.EXTRA_SELECTED_PRODUCTS);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSelected);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectedProductAdapter(selected != null ? selected : new ArrayList<>());
        recyclerView.setAdapter(adapter);

        Button btnSendEmail = findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(v -> sendEmail());
    }

    /**
     * Builds an email intent pre-filled with the recipient,
     * subject, and a formatted body listing each selected product's details.
     */
    private void sendEmail() {
        String recipient = getString(R.string.email_recipient);
        String subject = getString(R.string.email_subject);
        String body = buildEmailBody();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        emailLauncher.launch(intent);
    }

    /**
     * Formats the current adapter product list into a plain-text email body.
     */
    private String buildEmailBody() {
        StringBuilder sb = new StringBuilder();
        for (Product p : adapter.getProducts()) {
            sb.append("Name: ").append(p.getName()).append("\n");
            sb.append("Description: ").append(p.getDescription()).append("\n");
            sb.append("Seller: ").append(p.getSeller()).append("\n");
            sb.append(String.format(Locale.getDefault(), "Price: $%.2f", p.getPrice())).append("\n");
            sb.append("--------------------\n");
        }
        return sb.toString();
    }
}
