package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityViewReviewsBinding;

/**
 * Allows restaurant owners to view client reviews and contact information
 */
public class ViewReviewsActivity extends BaseActivity {

    private ActivityViewReviewsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_reviews);
        setActionBar(binding.toolbar);
        setActionBarTitle("View Reviews");
    }
}