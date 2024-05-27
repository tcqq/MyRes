package com.android.myres.ui.restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityRestaurantDetailBinding;

/**
 * Shows restaurant details including navigation and menu buttons
 */
public class RestaurantDetailActivity extends BaseActivity {

    private ActivityRestaurantDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_detail);
        setActionBar(binding.toolbar);
        setActionBarTitle("Restaurant Detail");

        // Assuming data is passed via intent
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("name");
        String restaurantAddress = intent.getStringExtra("address");
        String restaurantHours = intent.getStringExtra("hours");
        double restaurantRating = intent.getDoubleExtra("rating", 0.0);

        binding.restaurantName.setText(restaurantName);
        binding.restaurantAddress.setText(restaurantAddress);
        binding.restaurantHours.setText(restaurantHours);
        binding.restaurantRating.setText(String.format("Rating: %.1f", restaurantRating));

        binding.navigationButton.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(restaurantAddress));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        binding.menuButton.setOnClickListener(v -> {
            Intent menuIntent = new Intent(this, MenuClientActivity.class);
            menuIntent.putExtra("restaurantId", intent.getStringExtra("restaurantId")); // Pass restaurant ID
            startActivity(menuIntent);
        });
    }
}
