package com.android.myres.ui.service;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityViewServiceRequestsBinding;
import com.android.myres.ui.service.adapter.ServiceRequestAdapter;
import com.android.myres.ui.service.data.ServiceRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class ViewServiceRequestsActivity extends BaseActivity {

    private ActivityViewServiceRequestsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_service_requests);
        setActionBar(binding.toolbar);
        setActionBarTitle("View Service Requests");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ServiceRequest> serviceRequestList = new ArrayList<>();
        serviceRequestList.add(new ServiceRequest("Request #1", "Table 5 needs water", "Pending"));
        serviceRequestList.add(new ServiceRequest("Request #2", "Table 3 needs menu", "In Progress"));
        serviceRequestList.add(new ServiceRequest("Request #3", "Table 2 needs check", "Completed"));
        ServiceRequestAdapter adapter = new ServiceRequestAdapter(serviceRequestList);
        binding.recyclerView.setAdapter(adapter);
    }
}