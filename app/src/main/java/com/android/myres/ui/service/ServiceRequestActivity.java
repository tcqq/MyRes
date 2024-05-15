package com.android.myres.ui.service;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityServiceRequestBinding;

/**
 * Allows clients to request service, notifies waitresses
 */
public class ServiceRequestActivity extends BaseActivity {

    private ActivityServiceRequestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_request);
        setActionBar(binding.toolbar);
        setActionBarTitle("Service Request");
    }
}