package com.android.myres.ui.order;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.base.BaseActivity;
import com.android.myres.databinding.ActivityOrderTrackingBinding;

public class OrderTrackingActivity extends BaseActivity {

    private ActivityOrderTrackingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_tracking);
        setActionBar(binding.toolbar);
        setActionBarTitle("Order Tracking");
    }
}