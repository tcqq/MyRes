package com.android.myres.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityUserRegisterBinding;
import com.android.myres.ui.home.ClientHomeActivity;
import com.android.myres.ui.home.KitchenWorkerHomeActivity;
import com.android.myres.ui.home.OwnerHomeActivity;
import com.android.myres.ui.home.WaitressHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends BaseActivity {

    private ActivityUserRegisterBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_register);
        setActionBar(binding.toolbar);
        setActionBarTitle("User Register");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String[] genders = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genders);
        binding.gender.setAdapter(adapter);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String firstName = binding.name.getText().toString().trim();
        final String phone = binding.phone.getText().toString().trim();
        final String gender = binding.gender.getText().toString().trim();
        final String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            binding.name.setError("Enter first name");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            binding.phone.setError("Enter phone number");
            return;
        }

        if (TextUtils.isEmpty(gender)) {
            binding.gender.setError("Select gender");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            binding.email.setError("Enter email");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            binding.password.setError("Enter password");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                RadioGroup userTypeGroup = findViewById(R.id.user_type_group);
                                int selectedId = userTypeGroup.getCheckedRadioButtonId();
                                RadioButton selectedRadioButton = findViewById(selectedId);

                                if (selectedRadioButton != null) {
                                    String userType = selectedRadioButton.getText().toString();
                                    saveUserToFirestore(user, firstName, phone, gender, email, userType);
                                }
                            }
                        } else {
                            Toast.makeText(UserRegisterActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void saveUserToFirestore(FirebaseUser user, String firstName, String phone, String gender, String email, String userType) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("uid", user.getUid());
        userMap.put("firstName", firstName);
        userMap.put("phone", phone);
        userMap.put("gender", gender);
        userMap.put("email", email);
        userMap.put("role", userType);

        db.collection("users").document(user.getUid())
                .set(userMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserRegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            navigateToHome(userType);
                        } else {
                            Toast.makeText(UserRegisterActivity.this, "Failed to save user data", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void navigateToHome(String role) {
        if (role == null) {
            showToast("Role not defined for the user");
            return;
        }
        Intent intent;
        switch (role) {
            case "Client":
                intent = new Intent(this, ClientHomeActivity.class);
                break;
            case "Waitress":
                intent = new Intent(this, WaitressHomeActivity.class);
                break;
            case "Restaurant Owner":
                intent = new Intent(this, OwnerHomeActivity.class);
                break;
            case "Kitchen Worker":
                intent = new Intent(this, KitchenWorkerHomeActivity.class);
                break;
            default:
                showToast("Unknown role");
                return;
        }
        startActivity(intent);
        finish();
    }
}
