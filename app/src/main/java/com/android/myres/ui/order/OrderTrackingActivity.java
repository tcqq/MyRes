package com.android.myres.ui.order;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityOrderTrackingBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

/**
 * Displays order status and preparation time countdown
 */
public class OrderTrackingActivity extends BaseActivity {

    private ActivityOrderTrackingBinding binding;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis; // This will be dynamically set based on the preparation time
    private FirebaseFirestore db;
    private ListenerRegistration orderListener;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_tracking);
        setActionBar(binding.toolbar);
        setActionBarTitle("Order Tracking");

        db = FirebaseFirestore.getInstance();

        // Assuming orderId is passed via intent
        orderId = getIntent().getStringExtra("orderId");

        if (orderId != null) {
            listenToOrderUpdates();
        } else {
            Log.e("OrderTrackingActivity", "Order ID not provided");
            finish();
        }
    }

    private void listenToOrderUpdates() {
        DocumentReference orderRef = db.collection("orders").document(orderId);
        orderListener = orderRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                Log.w("OrderTrackingActivity", "Listen failed.", e);
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                String status = snapshot.getString("status");
                Long preparationTime = snapshot.getLong("preparationTime");

                if (status != null) {
                    binding.orderStatus.setText("Order Status: " + status);
                }

                if (preparationTime != null) {
                    timeLeftInMillis = preparationTime * 60 * 1000;
                    binding.orderExpectedTime.setText("Expected Time: " + preparationTime + " minutes");
                    startTimer();
                }

                if ("Ready".equals(status)) {
                    binding.orderStatus.setText("Order Status: Ready for Pickup");
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                }
            } else {
                Log.d("OrderTrackingActivity", "Current data: null");
            }
        });
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        binding.progressBar.setMax((int) timeLeftInMillis);
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                binding.orderStatus.setText("Order Status: Ready for Pickup");
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        binding.orderExpectedTime.setText("Expected Time: " + timeLeftFormatted);
        binding.progressBar.setProgress((int) timeLeftInMillis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderListener != null) {
            orderListener.remove();
        }
    }
}
