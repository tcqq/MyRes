package com.android.myres.ui.order;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.databinding.ActivityViewOrdersBinding;
import com.android.myres.ui.order.adapter.OrderAdapter;
import com.android.myres.ui.order.data.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class ViewOrdersActivity extends AppCompatActivity {

    private ActivityViewOrdersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_orders);
        setActionBar(binding.toolbar);
        setActionBarTitle("View Orders");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order("Order #1", "Dish A, Dish B", "Pending"));
        orderList.add(new Order("Order #2", "Dish C, Dish D", "In Progress"));
        orderList.add(new Order("Order #3", "Dish E, Dish F", "Completed"));
        OrderAdapter adapter = new OrderAdapter(orderList);
        binding.recyclerView.setAdapter(adapter);
    }

    private void setActionBar(androidx.appcompat.widget.Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}