package com.android.myres.ui.order;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityUpdateOrderStatusBinding;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class UpdateOrderStatusActivity extends BaseActivity {

    private ActivityUpdateOrderStatusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_order_status);
        setActionBar(binding.toolbar);
        setActionBarTitle("Update Order Status");

        String[] statuses = new String[]{"Pending", "In Progress", "Completed"};
        binding.orderStatus.setAdapter(new SpinnerAdapter(this, statuses));

        binding.updateButton.setOnClickListener(v -> {
            String orderNumber = binding.orderNumber.getText().toString().trim();
            String orderStatus = binding.orderStatus.getText().toString().trim();

            if (orderNumber.isEmpty() || orderStatus.isEmpty()) {
                showToast("Please fill in all fields");
            } else {
                showToast("Order Status Updated");
            }
        });
    }
}