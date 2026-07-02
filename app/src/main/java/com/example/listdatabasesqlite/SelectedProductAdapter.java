package com.example.listdatabasesqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * RecyclerView adapter for the selected-products list shown in SecondActivity.
 */
public class SelectedProductAdapter extends RecyclerView.Adapter<SelectedProductAdapter.ViewHolder> {

    private final List<Product> products;

    /**
     * @param products the list of products selected in MainActivity
     *                 a copy is made so the original list is not mutated
     */
    public SelectedProductAdapter(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selected_product, parent, false);
        return new ViewHolder(view);
    }

    /** Binds all product fields — image, name, price, seller, and description — to the row. */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.ivImage.setImageResource(product.getImageResId());
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        holder.tvSeller.setText(product.getSeller());
        holder.tvDescription.setText(product.getDescription());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Returns the live product list. Used by {@link SecondActivity#buildEmailBody()}
     * to format the email body from the current adapter state.
     *
     * @return the mutable list backing this adapter
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Removes all products from the adapter and notifies the RecyclerView.
     * Does not modify the SQLite database.
     */
    public void clearAll() {
        int size = products.size();
        products.clear();
        notifyItemRangeRemoved(0, size);
    }

    /** ViewHolder for a single selected-product row showing full product detail. */
    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivImage;
        final TextView tvName;
        final TextView tvPrice;
        final TextView tvSeller;
        final TextView tvDescription;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivSelectedImage);
            tvName = itemView.findViewById(R.id.tvSelectedName);
            tvPrice = itemView.findViewById(R.id.tvSelectedPrice);
            tvSeller = itemView.findViewById(R.id.tvSelectedSeller);
            tvDescription = itemView.findViewById(R.id.tvSelectedDescription);
        }
    }
}
