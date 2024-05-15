package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.base.BaseActivity;
import com.android.myres.databinding.ActivityReviewBinding;

public class ReviewActivity extends BaseActivity {

    private ActivityReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        setActionBar(binding.toolbar);
        setActionBarTitle("Review");
    }
}