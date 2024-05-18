package com.android.myres.ui.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.myres.R;
import com.android.myres.ui.order.data.Order;

import java.util.List;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderNumberTextView.setText(order.getOrderNumber());
        holder.orderDetailsTextView.setText(order.getOrderDetails());
        holder.orderStatusTextView.setText(order.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumberTextView;
        TextView orderDetailsTextView;
        TextView orderStatusTextView;

        ViewHolder(View itemView) {
            super(itemView);
            orderNumberTextView = itemView.findViewById(R.id.order_number_text_view);
            orderDetailsTextView = itemView.findViewById(R.id.order_details_text_view);
            orderStatusTextView = itemView.findViewById(R.id.order_status_text_view);
        }
    }
}