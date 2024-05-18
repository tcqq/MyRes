package com.android.myres.ui.service.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myres.R;
import com.android.myres.ui.service.data.ServiceRequest;

import java.util.List;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class ServiceRequestAdapter extends RecyclerView.Adapter<ServiceRequestAdapter.ViewHolder> {

    private List<ServiceRequest> serviceRequestList;

    public ServiceRequestAdapter(List<ServiceRequest> serviceRequestList) {
        this.serviceRequestList = serviceRequestList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceRequest serviceRequest = serviceRequestList.get(position);
        holder.requestNumberTextView.setText(serviceRequest.getRequestNumber());
        holder.requestDetailsTextView.setText(serviceRequest.getRequestDetails());
        holder.requestStatusTextView.setText(serviceRequest.getRequestStatus());
    }

    @Override
    public int getItemCount() {
        return serviceRequestList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView requestNumberTextView;
        TextView requestDetailsTextView;
        TextView requestStatusTextView;

        ViewHolder(View itemView) {
            super(itemView);
            requestNumberTextView = itemView.findViewById(R.id.request_number_text_view);
            requestDetailsTextView = itemView.findViewById(R.id.request_details_text_view);
            requestStatusTextView = itemView.findViewById(R.id.request_status_text_view);
        }
    }
}