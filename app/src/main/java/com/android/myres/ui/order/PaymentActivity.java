package com.android.myres.ui.order;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityPaymentBinding;

/**
 * Simulates payment functionality and shows total order price
 */
public class PaymentActivity extends BaseActivity {

    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        setActionBar(binding.toolbar);
        setActionBarTitle("Payment");
    }
}