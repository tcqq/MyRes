package com.android.myres.ui.review;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityReviewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows users to leave a review for a restaurant
 */
public class ReviewActivity extends BaseActivity {

    private ActivityReviewBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        setActionBar(binding.toolbar);
        setActionBarTitle("Leave a Review");

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Assuming restaurantId is passed via intent
        restaurantId = getIntent().getStringExtra("restaurantId");

        binding.submitButton.setOnClickListener(v -> submitReview());
    }

    private void submitReview() {
        String reviewText = binding.reviewText.getText().toString().trim();
        float rating = binding.ratingBar.getRating();

        if (reviewText.isEmpty() || rating == 0) {
            Toast.makeText(this, "Please provide a rating and a review", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference reviewRef = firestore.collection("restaurants")
                .document(restaurantId)
                .collection("reviews")
                .document();

        Map<String, Object> review = new HashMap<>();
        review.put("userId", userId);
        review.put("rating", rating);
        review.put("reviewText", reviewText);
        review.put("timestamp", System.currentTimeMillis());

        reviewRef.set(review)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Review Submitted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to submit review", Toast.LENGTH_SHORT).show());
    }
}
