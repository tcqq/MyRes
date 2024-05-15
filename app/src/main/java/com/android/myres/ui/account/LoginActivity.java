package com.android.myres.ui.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityLoginBinding;

/**
 * Login page with email, password fields, and role selection, redirects to respective home page
 */
public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setActionBar(binding.toolbar);
        setActionBarTitle("Login");

        String[] userRoles = new String[]{"Client", "Waitress", "Restaurant Owner", "Kitchen Worker"};
        binding.role.setAdapter(new SpinnerAdapter(this, userRoles));

        binding.signIn.setOnClickListener(view -> {
            String selectedRole = binding.role.getText().toString();
            showToast("Sign in: " + selectedRole);
        });
    }
}