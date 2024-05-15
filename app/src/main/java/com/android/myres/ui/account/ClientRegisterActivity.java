package com.android.myres.ui.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityClientRegisterBinding;

/**
 * Client registration, including form fields for name, phone, gender, email, and password
 */
public class ClientRegisterActivity extends BaseActivity {

    private ActivityClientRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_register);
        setActionBar(binding.toolbar);
        setActionBarTitle("Client Register");
    }
}