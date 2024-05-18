package com.android.myres.ui.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityAddWaitressBinding;

/**
 * Form for creating a new waitress account
 */
public class AddWaitressActivity extends BaseActivity {

    private ActivityAddWaitressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_waitress);
        setActionBar(binding.toolbar);
        setActionBarTitle("Add Waitress");

        String[] genders = new String[] {"Male", "Female"};
        binding.waitressGender.setAdapter(new SpinnerAdapter(this, genders));

        binding.addButton.setOnClickListener(v -> {
            String waitressName = binding.waitressName.getText().toString().trim();
            String waitressPhone = binding.waitressPhone.getText().toString().trim();
            String waitressGender = binding.waitressGender.getText().toString().trim();

            if (waitressName.isEmpty() || waitressPhone.isEmpty() || waitressGender.isEmpty()) {
                showToast("Please fill in all fields");
            } else {
                showToast("Waitress Added");
            }
        });
    }
}