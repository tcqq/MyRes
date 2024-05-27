package com.android.myres.ui.order;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityViewOrdersBinding;
import com.android.myres.ui.order.adapter.OrderAdapter;
import com.android.myres.ui.order.data.Order;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a list of orders for the kitchen worker
 */
public class ViewOrdersActivity extends BaseActivity {

    private ActivityViewOrdersBinding binding;
    private FirebaseFirestore firestore;
    private OrderAdapter adapter;
    private List<Order> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_orders);
        setActionBar(binding.toolbar);
        setActionBarTitle("View Orders");

        firestore = FirebaseFirestore.getInstance();
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        loadOrders();
    }

    private void loadOrders() {
        firestore.collection("orders")
                .whereEqualTo("status", "Received")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        orderList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Order order = document.toObject(Order.class);
                            orderList.add(order);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        showToast("Failed to load orders");
                    }
                });
    }
}
