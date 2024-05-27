package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityViewReviewsBinding;
import com.android.myres.ui.restaurant.adapter.ReviewAdapter;
import com.android.myres.ui.restaurant.data.Review;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows restaurant owners to view client reviews and contact information
 */
public class ViewReviewsActivity extends BaseActivity {

    private ActivityViewReviewsBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_reviews);
        setActionBar(binding.toolbar);
        setActionBarTitle("View Reviews");

        db = FirebaseFirestore.getInstance();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadReviews();
    }

    private void loadReviews() {
        db.collection("reviews")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Review> reviewList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            int rating = document.getLong("rating").intValue();
                            String comment = document.getString("comment");
                            String contact = document.getString("contact");
                            reviewList.add(new Review(name, rating, comment, contact));
                        }
                        ReviewAdapter adapter = new ReviewAdapter(reviewList);
                        binding.recyclerView.setAdapter(adapter);
                    } else {
                        showToast("Failed to load reviews");
                    }
                });
    }
}
