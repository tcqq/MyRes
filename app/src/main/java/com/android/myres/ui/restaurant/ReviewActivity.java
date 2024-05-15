package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityReviewBinding;

/**
 * Allows clients to leave reviews after payment
 */
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