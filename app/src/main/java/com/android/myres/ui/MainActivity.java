package com.android.myres.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityMainBinding;
import com.android.myres.ui.account.AddKitchenWorkerActivity;
import com.android.myres.ui.account.AddWaitressActivity;
import com.android.myres.ui.account.ClientRegisterActivity;
import com.android.myres.ui.account.LoginActivity;
import com.android.myres.ui.home.ClientHomeActivity;
import com.android.myres.ui.home.KitchenWorkerHomeActivity;
import com.android.myres.ui.home.OwnerHomeActivity;
import com.android.myres.ui.home.WaitressHomeActivity;
import com.android.myres.ui.order.OrderTrackingActivity;
import com.android.myres.ui.order.PaymentActivity;
import com.android.myres.ui.restaurant.AddRestaurantActivity;
import com.android.myres.ui.restaurant.MenuActivity;
import com.android.myres.ui.restaurant.MenuManagementActivity;
import com.android.myres.ui.restaurant.RestaurantDetailActivity;
import com.android.myres.ui.restaurant.ReviewActivity;
import com.android.myres.ui.restaurant.UpdateRestaurantActivity;
import com.android.myres.ui.restaurant.ViewReviewsActivity;
import com.android.myres.ui.service.ServiceRequestActivity;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setActionBar(binding.toolbar);
        setActionBarTitle(R.string.app_name);

        binding.clientRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, ClientRegisterActivity.class));
        });
        binding.login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
        binding.clientHome.setOnClickListener(v -> {
            startActivity(new Intent(this, ClientHomeActivity.class));
        });
        binding.restaurantDetail.setOnClickListener(v -> {
            startActivity(new Intent(this, RestaurantDetailActivity.class));
        });
        binding.menu.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuActivity.class));
        });
        binding.orderTracking.setOnClickListener(v -> {
            startActivity(new Intent(this, OrderTrackingActivity.class));
        });
        binding.payment.setOnClickListener(v -> {
            startActivity(new Intent(this, PaymentActivity.class));
        });
        binding.review.setOnClickListener(v -> {
            startActivity(new Intent(this, ReviewActivity.class));
        });
        binding.serviceRequest.setOnClickListener(v -> {
            startActivity(new Intent(this, ServiceRequestActivity.class));
        });
        binding.ownerHome.setOnClickListener(v -> {
            startActivity(new Intent(this, OwnerHomeActivity.class));
        });
        binding.addRestaurant.setOnClickListener(v -> {
            startActivity(new Intent(this, AddRestaurantActivity.class));
        });
        binding.updateRestaurant.setOnClickListener(v -> {
            startActivity(new Intent(this, UpdateRestaurantActivity.class));
        });
        binding.menuManagement.setOnClickListener(v -> {
            startActivity(new Intent(this, MenuManagementActivity.class));
        });
        binding.addWaitress.setOnClickListener(v -> {
            startActivity(new Intent(this, AddWaitressActivity.class));
        });
        binding.addKitchenWorker.setOnClickListener(v -> {
            startActivity(new Intent(this, AddKitchenWorkerActivity.class));
        });
        binding.viewReviews.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewReviewsActivity.class));
        });
        binding.waitressHome.setOnClickListener(v -> {
            startActivity(new Intent(this, WaitressHomeActivity.class));
        });
        binding.kitchenWorkerHome.setOnClickListener(v -> {
            startActivity(new Intent(this, KitchenWorkerHomeActivity.class));
        });
    }

}