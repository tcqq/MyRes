package com.android.myres.ui.restaurant;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityUpdateRestaurantBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows restaurant owners to update restaurant details
 */
public class UpdateRestaurantActivity extends BaseActivity {

    private ActivityUpdateRestaurantBinding binding;
    private List<String> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_restaurant);
        setActionBar(binding.toolbar);
        setActionBarTitle("Update Restaurant");

        restaurantList = new ArrayList<>();
        restaurantList.add("Restaurant A");
        restaurantList.add("Restaurant B");
        restaurantList.add("Restaurant C");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, restaurantList);
        binding.selectRestaurant.setAdapter(adapter);

        binding.selectRestaurant.setOnItemClickListener((parent, view, position, id) -> {
            String selectedRestaurant = adapter.getItem(position);
            fillRestaurantData(selectedRestaurant);
        });

        binding.updateButton.setOnClickListener(v -> {
            String restaurantName = binding.restaurantName.getText().toString().trim();
            String restaurantAddress = binding.restaurantAddress.getText().toString().trim();
            String restaurantHours = binding.restaurantHours.getText().toString().trim();

            if (restaurantName.isEmpty() || restaurantAddress.isEmpty() || restaurantHours.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Restaurant Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillRestaurantData(String restaurantName) {
        switch (restaurantName) {
            case "Restaurant A":
                binding.restaurantName.setText("Restaurant A");
                binding.restaurantAddress.setText("123 Main St, City, Country");
                binding.restaurantHours.setText("9:00 AM - 9:00 PM");
                break;
            case "Restaurant B":
                binding.restaurantName.setText("Restaurant B");
                binding.restaurantAddress.setText("456 Elm St, City, Country");
                binding.restaurantHours.setText("10:00 AM - 10:00 PM");
                break;
            case "Restaurant C":
                binding.restaurantName.setText("Restaurant C");
                binding.restaurantAddress.setText("789 Oak St, City, Country");
                binding.restaurantHours.setText("8:00 AM - 8:00 PM");
                break;
        }
    }
}