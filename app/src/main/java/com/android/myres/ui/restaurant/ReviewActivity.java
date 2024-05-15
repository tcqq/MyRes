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

        binding.submitButton.setOnClickListener(v -> {
            float rating = binding.ratingBar.getRating();
            String reviewText = binding.reviewText.getText().toString().trim();

            if (rating == 0) {
                showToast("Please provide a rating");
            } else if (reviewText.isEmpty()) {
                showToast("Please write a review");
            } else {
                showToast("Review Submitted");
            }
        });
    }
}