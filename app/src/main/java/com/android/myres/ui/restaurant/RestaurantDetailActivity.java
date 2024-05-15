package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.base.BaseActivity;
import com.android.myres.databinding.ActivityRestaurantDetailBinding;

public class RestaurantDetailActivity extends BaseActivity {

    private ActivityRestaurantDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_detail);
        setActionBar(binding.toolbar);
        setActionBarTitle("Restaurant Detail");
    }
}