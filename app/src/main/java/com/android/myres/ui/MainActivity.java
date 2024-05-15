package com.android.myres.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setActionBar(binding.toolbar);
        setActionBarTitle(R.string.app_name);

        binding.clientRegister.setOnClickListener(v -> showToast("Client Register"));
        binding.login.setOnClickListener(v -> showToast("Login"));
        binding.clientHome.setOnClickListener(v -> showToast("Client Home"));
        binding.restaurantDetail.setOnClickListener(v -> showToast("Restaurant Detail"));
        binding.menu.setOnClickListener(v -> showToast("Menu"));
        binding.orderTracking.setOnClickListener(v -> showToast("Order Tracking"));
        binding.payment.setOnClickListener(v -> showToast("Payment"));
        binding.review.setOnClickListener(v -> showToast("Review"));
        binding.serviceRequest.setOnClickListener(v -> showToast("Service Request"));
        binding.ownerHome.setOnClickListener(v -> showToast("Owner Home"));
        binding.addRestaurant.setOnClickListener(v -> showToast("Add Restaurant"));
        binding.updateRestaurant.setOnClickListener(v -> showToast("Update Restaurant"));
        binding.menuManagement.setOnClickListener(v -> showToast("Menu Management"));
        binding.addWaitress.setOnClickListener(v -> showToast("Add Waitress"));
        binding.addKitchenWorker.setOnClickListener(v -> showToast("Add Kitchen Worker"));
        binding.viewReviews.setOnClickListener(v -> showToast("View Reviews"));
        binding.waitressHome.setOnClickListener(v -> showToast("Waitress Home"));
        binding.kitchenWorkerHome.setOnClickListener(v -> showToast("Kitchen Worker Home"));
    }
}