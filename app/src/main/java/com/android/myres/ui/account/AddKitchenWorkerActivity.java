package com.android.myres.ui.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityAddKitchenWorkerBinding;

/**
 * Form for creating a new kitchen worker account
 */
public class AddKitchenWorkerActivity extends BaseActivity {

    private ActivityAddKitchenWorkerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_kitchen_worker);
        setActionBar(binding.toolbar);
        setActionBarTitle("Add Kitchen Worker");

        String[] genders = new String[] {"Male", "Female"};
        binding.workerGender.setAdapter(new SpinnerAdapter(this, genders));

        binding.addButton.setOnClickListener(v -> {
            String workerName = binding.workerName.getText().toString().trim();
            String workerPhone = binding.workerPhone.getText().toString().trim();
            String workerGender = binding.workerGender.getText().toString().trim();

            if (workerName.isEmpty() || workerPhone.isEmpty() || workerGender.isEmpty()) {
                showToast("Please fill in all fields");
            } else {
                showToast("Kitchen Worker Added");
            }
        });
    }
}