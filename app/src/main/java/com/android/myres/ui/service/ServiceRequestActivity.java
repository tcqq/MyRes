package com.android.myres.ui.service;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityServiceRequestBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

/**
 * Allows clients to request service, notifies waitresses
 */
public class ServiceRequestActivity extends BaseActivity {

    private ActivityServiceRequestBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_request);
        setActionBar(binding.toolbar);
        setActionBarTitle("Service Request");

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        binding.requestServiceButton.setOnClickListener(v -> requestService());
    }

    private void requestService() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        String userName = firebaseAuth.getCurrentUser().getDisplayName();

        Map<String, Object> serviceRequest = new HashMap<>();
        serviceRequest.put("userId", userId);
        serviceRequest.put("userName", userName);
        serviceRequest.put("timestamp", System.currentTimeMillis());

        firestore.collection("service_requests")
                .add(serviceRequest)
                .addOnSuccessListener(documentReference -> {
                    showToast("Service Requested");
                    sendNotificationToWaitresses();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to request service", Toast.LENGTH_SHORT).show());
    }

    private void sendNotificationToWaitresses() {
        FirebaseMessaging.getInstance().subscribeToTopic("waitresses")
                .addOnCompleteListener(task -> {
                    String msg = "Service Request Sent";
                    if (!task.isSuccessful()) {
                        msg = "Failed to send service request";
                    }
                    showToast(msg);
                });
    }
}
