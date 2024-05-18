package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityViewReviewsBinding;
import com.android.myres.ui.restaurant.adapter.ReviewAdapter;
import com.android.myres.ui.restaurant.data.Review;

import java.util.ArrayList;
import java.util.List;

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

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review("John Doe", 5, "Great service and delicious food!"));
        reviewList.add(new Review("Jane Smith", 4, "Good experience, but a bit pricey."));
        reviewList.add(new Review("Alice Johnson", 3, "Average food, nothing special."));
        ReviewAdapter adapter = new ReviewAdapter(reviewList);
        binding.recyclerView.setAdapter(adapter);
    }
}