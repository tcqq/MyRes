package com.android.myres.ui.order;

import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityOrderTrackingBinding;

/**
 * Displays order status and preparation time countdown
 */
public class OrderTrackingActivity extends BaseActivity {

    private ActivityOrderTrackingBinding binding;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 30 * 60 * 1000; // 30 minutes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_tracking);
        setActionBar(binding.toolbar);
        setActionBarTitle("Order Tracking");

        binding.orderStatus.setText("Order Status: In Progress");
        binding.orderExpectedTime.setText("Expected Time: 30 minutes");

        startTimer();
    }

    private void startTimer() {
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
}