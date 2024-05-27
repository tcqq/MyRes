package com.android.myres.ui.order;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityPaymentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulates payment functionality and shows total order price
 */
public class PaymentActivity extends BaseActivity {

    private ActivityPaymentBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment);
        setActionBar(binding.toolbar);
        setActionBarTitle("Payment");

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // Assuming order total is passed via intent
        String orderTotal = getIntent().getStringExtra("orderTotal");
        if (orderTotal == null) {
            orderTotal = "0";
        }

        binding.orderTotal.setText("Order Total: $" + orderTotal);

        String finalOrderTotal = orderTotal;
        binding.payButton.setOnClickListener(v -> {
            String cardNumber = binding.cardNumber.getText().toString().trim();
            String cardExpiry = binding.cardExpiry.getText().toString().trim();
            String cardCVC = binding.cardCvc.getText().toString().trim();

            if (cardNumber.isEmpty() || cardExpiry.isEmpty() || cardCVC.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                processPayment(finalOrderTotal);
            }
        });
    }

    private void processPayment(String orderTotal) {
        // Simulate payment process
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();

        // Update order list in Firestore
        String userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference orderRef = firestore.collection("orders").document();

        Map<String, Object> order = new HashMap<>();
        order.put("userId", userId);
        order.put("total", orderTotal);
        order.put("status", "Paid");
        order.put("timestamp", System.currentTimeMillis());

        orderRef.set(order)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Order Updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update order", Toast.LENGTH_SHORT).show());
    }
}
