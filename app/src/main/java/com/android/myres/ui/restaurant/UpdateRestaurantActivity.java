package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityUpdateRestaurantBinding;

/**
 * Allows restaurant owners to update restaurant details
 */
public class UpdateRestaurantActivity extends BaseActivity {

    private ActivityUpdateRestaurantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_restaurant);
        setActionBar(binding.toolbar);
        setActionBarTitle("Update Restaurant");
    }
}