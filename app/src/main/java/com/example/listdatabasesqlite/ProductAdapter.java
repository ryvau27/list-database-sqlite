package com.example.listdatabasesqlite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * RecyclerView adapter for the product catalog shown in MainActivity.
 *
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    /**
     * Callback invoked whenever the number of selected products changes.
     */
    public interface OnSelectionChangedListener {
        /**
         * @param selectedCount the current number of selected products
         */
        void onSelectionChanged(int selectedCount);
    }

    private final List<Product> products;
    /** Tracks selected products by ID to remain stable if the list order ever changes. */
    private final Set<Integer> selectedIds = new HashSet<>();
    private final OnSelectionChangedListener listener;

    /**
     * @param products the full product catalog to display
     * @param listener callback notified whenever the selection count changes
     */
    public ProductAdapter(List<Product> products, OnSelectionChangedListener listener) {
        this.products = products;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds product data to the row and attaches a click listener that toggles
     * selection. 
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.ivProductImage.setImageResource(product.getImageResId());
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        holder.cbSelected.setChecked(selectedIds.contains(product.getId()));

        holder.itemView.setOnClickListener(v -> {
            int id = product.getId();
            if (selectedIds.contains(id)) {
                selectedIds.remove(id);
            } else {
                selectedIds.add(id);
            }
            notifyItemChanged(holder.getAdapterPosition());
            listener.onSelectionChanged(selectedIds.size());
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * Returns a list of products whose IDs are currently in the selection,
     * in the same order they appear in the full catalog.
     */
    public List<Product> getSelectedProducts() {
        List<Product> selected = new ArrayList<>();
        for (Product p : products) {
            if (selectedIds.contains(p.getId())) {
                selected.add(p);
            }
        }
        return selected;
    }

    /** ViewHolder for a single product row in the catalog list. */
    static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivProductImage;
        final TextView tvProductName;
        final TextView tvProductPrice;
        final CheckBox cbSelected;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            cbSelected = itemView.findViewById(R.id.cbSelected);
        }
    }
}
