package com.android.myres.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityOwnerHomeBinding;
import com.android.myres.ui.account.AddKitchenWorkerActivity;
import com.android.myres.ui.account.AddWaitressActivity;
import com.android.myres.ui.restaurant.AddRestaurantActivity;
import com.android.myres.ui.restaurant.MenuManagementActivity;
import com.android.myres.ui.restaurant.UpdateRestaurantActivity;
import com.android.myres.ui.restaurant.ViewReviewsActivity;

/**
 * Restaurant owner home page with management functionalities
 */
public class OwnerHomeActivity extends BaseActivity {

    private ActivityOwnerHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_owner_home);
        setActionBar(binding.toolbar);
        setActionBarTitle("Owner Home");

        binding.addRestaurantButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddRestaurantActivity.class);
            startActivity(intent);
        });

        binding.updateRestaurantButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateRestaurantActivity.class);
            startActivity(intent);
        });

        binding.menuManagementButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuManagementActivity.class);
            startActivity(intent);
        });

        binding.addWaitressButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddWaitressActivity.class);
            startActivity(intent);
        });

        binding.addKitchenWorkerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddKitchenWorkerActivity.class);
            startActivity(intent);
        });

        binding.viewReviewsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewReviewsActivity.class);
            startActivity(intent);
        });
    }
}