package com.android.myres.ui.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.base.BaseActivity;
import com.android.myres.databinding.ActivityClientHomeBinding;

public class ClientHomeActivity extends BaseActivity {

    private ActivityClientHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_home);
        setActionBar(binding.toolbar);
        setActionBarTitle("Client Home");
    }
}