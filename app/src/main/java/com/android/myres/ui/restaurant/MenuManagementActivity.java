package com.android.myres.ui.restaurant;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityMenuManagementBinding;

/**
 * Allows adding, updating, or deleting menu items
 */
public class MenuManagementActivity extends BaseActivity {

    private ActivityMenuManagementBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_management);
        setActionBar(binding.toolbar);
        setActionBarTitle("Menu Management");

        binding.addDishButton.setOnClickListener(v -> showToast("AddDish"));
        binding.updateDishButton.setOnClickListener(v -> showToast("UpdateDish"));
        binding.deleteDishButton.setOnClickListener(v -> showToast("DeleteDish"));
    }
}