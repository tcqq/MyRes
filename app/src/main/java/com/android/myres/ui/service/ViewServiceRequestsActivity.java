package com.android.myres.ui.service;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityViewServiceRequestsBinding;
import com.android.myres.ui.service.data.ServiceRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a list of service requests
 */
public class ViewServiceRequestsActivity extends BaseActivity {

    private ActivityViewServiceRequestsBinding binding;
    private FirebaseFirestore firestore;
    private com.android.myres.ui.service.ServiceRequestAdapter adapter;
    private List<ServiceRequest> serviceRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_service_requests);
        setActionBar(binding.toolbar);
        setActionBarTitle("Service Requests");

        firestore = FirebaseFirestore.getInstance();
        serviceRequestList = new ArrayList<>();
        adapter = new com.android.myres.ui.service.ServiceRequestAdapter(serviceRequestList);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        loadServiceRequests();
    }

    private void loadServiceRequests() {
        firestore.collection("service_requests")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        serviceRequestList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ServiceRequest serviceRequest = document.toObject(ServiceRequest.class);
                            serviceRequestList.add(serviceRequest);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        showToast("Failed to load service requests");
                    }
                });
    }
}
