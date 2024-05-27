package com.android.myres.ui.restaurant.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myres.R;
import com.android.myres.ui.restaurant.data.MenuItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuOldAdapter extends RecyclerView.Adapter<MenuOldAdapter.MenuViewHolder> {

    private List<MenuItem> menuItems;

    public MenuOldAdapter(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);
        holder.title.setText(menuItem.getTitle());
        holder.description.setText(menuItem.getDescription());
        holder.price.setText(String.format("$%.2f", menuItem.getPrice()));
        Picasso.get().load(menuItem.getImageUrl()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, price;
        ImageView image;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menu_item_title);
            description = itemView.findViewById(R.id.menu_item_description);
            price = itemView.findViewById(R.id.menu_item_price);
            image = itemView.findViewById(R.id.menu_item_image);
        }
    }
}
