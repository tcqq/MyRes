package com.android.myres.ui.order;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityUpdateOrderStatusBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows kitchen workers to update order status
 */
public class UpdateOrderStatusActivity extends BaseActivity {

    private ActivityUpdateOrderStatusBinding binding;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_order_status);
        setActionBar(binding.toolbar);
        setActionBarTitle("Update Order Status");

        firestore = FirebaseFirestore.getInstance();

        // Populate the dropdown menu with order statuses
        String[] orderStatuses = new String[]{"Received", "In preparation", "Ready"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, orderStatuses);
        binding.orderStatus.setAdapter(adapter);

        binding.updateButton.setOnClickListener(v -> updateOrderStatus());
    }

    private void updateOrderStatus() {
        String orderNumber = binding.orderNumber.getText().toString().trim();
        String status = binding.orderStatus.getText().toString().trim();

        if (orderNumber.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DocumentReference orderRef = firestore.collection("orders").document(orderNumber);
        orderRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> updates = new HashMap<>();
                        updates.put("status", status);

                        orderRef.update(updates)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(UpdateOrderStatusActivity.this, "Order Status Updated", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(UpdateOrderStatusActivity.this, "Failed to update order status", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(UpdateOrderStatusActivity.this, "Order not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateOrderStatusActivity.this, "Failed to retrieve order", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
