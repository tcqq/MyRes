package com.android.myres.ui.service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myres.R;
import com.android.myres.ui.service.data.ServiceRequest;

import java.util.List;

public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ServiceRequestViewHolder> {

    private List<ServiceRequest> serviceRequestList;

    public ServiceRequestAdapter(List<ServiceRequest> serviceRequestList) {
        this.serviceRequestList = serviceRequestList;
    }

    @NonNull
    @Override
    public ServiceRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_request, parent, false);
        return new ServiceRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceRequestViewHolder holder, int position) {
        ServiceRequest serviceRequest = serviceRequestList.get(position);
        holder.userNameTextView.setText(serviceRequest.getUserName());
        holder.timestampTextView.setText(String.valueOf(serviceRequest.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return serviceRequestList.size();
    }

    static class ServiceRequestViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTextView;
        TextView timestampTextView;

        public ServiceRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.user_name_text_view);
            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
        }
    }
}
