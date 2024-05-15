package com.android.myres.ui.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.base.BaseActivity;
import com.android.myres.databinding.ActivityAddWaitressBinding;

public class AddWaitressActivity extends BaseActivity {

    private ActivityAddWaitressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_waitress);
        setActionBar(binding.toolbar);
        setActionBarTitle("Add Waitress");
    }
}