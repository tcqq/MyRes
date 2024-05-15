package com.android.myres.ui.home;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityClientHomeBinding;

/**
 * Displays restaurant list with search and sorting options
 */
public class ClientHomeActivity extends BaseActivity {

    private ActivityClientHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_home);
        setActionBar(binding.toolbar);
        setActionBarTitle("Client Home");

        String[] sorts = new String[] {"Sort by Distance", "Sort by Price", "Sort by Rating"};
        binding.sortBy.setAdapter(new SpinnerAdapter(this, sorts));
    }
}