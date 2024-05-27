package com.android.myres.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityWaitressHomeBinding;
import com.android.myres.ui.order.ViewOrdersActivity;
import com.android.myres.ui.service.ViewServiceRequestsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Waitress home page with order list and service request notifications
 */
public class WaitressHomeActivity extends BaseActivity {

    private ActivityWaitressHomeBinding binding;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_waitress_home);
        setActionBar(binding.toolbar);
        setActionBarTitle("Waitress Home");

        firebaseAuth = FirebaseAuth.getInstance();

        // Subscribe to the "waitresses" topic to receive service request notifications
        FirebaseMessaging.getInstance().subscribeToTopic("waitresses")
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed to service requests";
                    if (!task.isSuccessful()) {
                        msg = "Subscription to service requests failed";
                    }
                    showToast(msg);
                });

        binding.viewOrdersButton.setOnClickListener(v -> startActivity(new Intent(this, ViewOrdersActivity.class)));
        binding.viewServiceRequestsButton.setOnClickListener(v -> startActivity(new Intent(this, ViewServiceRequestsActivity.class)));
    }
}
