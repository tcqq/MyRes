package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityAddRestaurantBinding;

/**
 * Form for adding restaurant details and building menu
 */
public class AddRestaurantActivity extends BaseActivity {

    private ActivityAddRestaurantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_restaurant);
        setActionBar(binding.toolbar);
        setActionBarTitle("Add Restaurant");

        binding.saveButton.setOnClickListener(v -> {
            String restaurantName = binding.restaurantName.getText().toString().trim();
            String restaurantAddress = binding.restaurantAddress.getText().toString().trim();
            String restaurantHours = binding.restaurantHours.getText().toString().trim();

            if (restaurantName.isEmpty() || restaurantAddress.isEmpty() || restaurantHours.isEmpty()) {
                showToast("Please fill in all fields");
            } else {
                showToast("Restaurant Saved");
            }
        });
    }
}