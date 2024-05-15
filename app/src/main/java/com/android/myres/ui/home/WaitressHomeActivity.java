package com.android.myres.ui.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.base.BaseActivity;
import com.android.myres.databinding.ActivityWaitressHomeBinding;

public class WaitressHomeActivity extends BaseActivity {

    private ActivityWaitressHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_waitress_home);
        setActionBar(binding.toolbar);
        setActionBarTitle("Waitress Home");
    }
}