package com.android.myres.ui.order;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityPaymentBinding;

/**
 * Simulates payment functionality and shows total order price
 */
public class PaymentActivity extends BaseActivity {

    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        setActionBar(binding.toolbar);
        setActionBarTitle("Payment");

        binding.orderTotal.setText("Order Total: $50");

        binding.payButton.setOnClickListener(v -> {
            String cardNumber = binding.cardNumber.getText().toString().trim();
            String cardExpiry = binding.cardExpiry.getText().toString().trim();
            String cardCVC = binding.cardCvc.getText().toString().trim();

            if (cardNumber.isEmpty() || cardExpiry.isEmpty() || cardCVC.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
}