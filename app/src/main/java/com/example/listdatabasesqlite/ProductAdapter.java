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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    public interface OnSelectionChangedListener {
        void onSelectionChanged(int selectedCount);
    }

    private final List<Product> products;
    private final Set<Integer> selectedIds = new HashSet<>();
    private final OnSelectionChangedListener listener;

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

    public List<Product> getSelectedProducts() {
        List<Product> selected = new ArrayList<>();
        for (Product p : products) {
            if (selectedIds.contains(p.getId())) {
                selected.add(p);
            }
        }
        return selected;
    }

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
