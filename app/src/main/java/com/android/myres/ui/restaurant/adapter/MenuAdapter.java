package com.android.myres.ui.restaurant.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myres.databinding.ItemMenuBinding;
import com.android.myres.ui.restaurant.data.MenuItem;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuItems;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MenuItem menuItem);
    }

    public MenuAdapter(List<MenuItem> menuItems, OnItemClickListener onItemClickListener) {
        this.menuItems = menuItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemMenuBinding itemBinding = ItemMenuBinding.inflate(layoutInflater, parent, false);
        return new MenuViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);
        holder.bind(menuItem, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        private final ItemMenuBinding binding;

        public MenuViewHolder(ItemMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MenuItem menuItem, OnItemClickListener onItemClickListener) {
            binding.setMenuItem(menuItem);
            binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(menuItem));
            binding.executePendingBindings();
        }
    }
}
