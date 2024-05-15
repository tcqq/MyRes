package com.android.myres.ui.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityOwnerHomeBinding;

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
    }
}