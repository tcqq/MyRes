package com.android.myres.ui.restaurant.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myres.R;
import com.android.myres.databinding.ItemRestaurantBinding;
import com.android.myres.ui.restaurant.MenuClientActivity;
import com.android.myres.ui.restaurant.data.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRestaurantBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.binding.setRestaurant(restaurant);
        holder.binding.executePendingBindings();

        // Load image using Picasso
        Picasso.get().load(restaurant.getImageUrl()).into(holder.binding.restaurantImage);

        // Set onClick listeners for icons
        holder.binding.mapsIcon.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(restaurant.getAddress()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        });

        holder.binding.wazeIcon.setOnClickListener(v -> {
            String url = "https://waze.com/ul?q=" + Uri.encode(restaurant.getAddress());
            Intent wazeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(wazeIntent);
        });

        holder.binding.menuIcon.setOnClickListener(v -> {
            // Implement menu icon click logic here
//            Intent menuIntent = new Intent(context, MenuClientActivity.class);
//          //  menuIntent.putExtra("restaurantId", intent.getStringExtra("restaurantId")); // Pass restaurant ID
//            context.startActivity(menuIntent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void updateList(List<Restaurant> newList) {
        restaurantList = newList;
        notifyDataSetChanged();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final ItemRestaurantBinding binding;

        public RestaurantViewHolder(ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
