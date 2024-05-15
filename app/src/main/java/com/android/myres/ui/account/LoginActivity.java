package com.android.myres.ui.account;

import android.content.Intent;
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

        binding.createAccount.setOnClickListener( view -> {
            startActivity(new Intent(this, ClientRegisterActivity.class));
        });
        binding.signIn.setOnClickListener(view -> {
            String selectedRole = binding.role.getText().toString();
            if (selectedRole.isEmpty()) {
                showToast("Please select a role");
            } else {
                int roleId = getRoleId(selectedRole);
                switch (roleId) {
                    case 0:
                        showToast("Sign in: Client");
                        break;
                    case 1:
                        showToast("Sign in: Waitress");
                        break;
                    case 2:
                        showToast("Sign in: Restaurant Owner");
                        break;
                    case 3:
                        showToast("Sign in: Kitchen Worker");
                        break;
                    default:
                        showToast("Unknown role");
                        break;
                }
            }
        });
    }

    private int getRoleId(String role) {
        switch (role) {
            case "Client":
                return 0;
            case "Waitress":
                return 1;
            case "Restaurant Owner":
                return 2;
            case "Kitchen Worker":
                return 3;
            default:
                return -1;
        }
    }
}