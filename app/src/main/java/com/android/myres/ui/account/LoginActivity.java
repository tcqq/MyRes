package com.android.myres.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityLoginBinding;
import com.android.myres.ui.home.ClientHomeActivity;
import com.android.myres.ui.home.KitchenWorkerHomeActivity;
import com.android.myres.ui.home.OwnerHomeActivity;
import com.android.myres.ui.home.WaitressHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Login page with email, password fields, and role selection, redirects to respective home page
 */
public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String[] userRoles = new String[]{"Client", "Waitress", "Restaurant Owner", "Kitchen Worker"};
        binding.role.setAdapter(new SpinnerAdapter(this, userRoles));

        binding.createAccount.setOnClickListener(view -> startActivity(new Intent(this, UserRegisterActivity.class)));

        binding.signIn.setOnClickListener(view -> {
            String email = binding.email.getText().toString().trim();
            String password = binding.password.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                showToast("All fields are required");
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    fetchUserRole(user);
                                }
                            } else {
                                showToast("Authentication failed: " + task.getException().getMessage());
                            }
                        }
                    });
        });
    }

    private void fetchUserRole(FirebaseUser user) {
        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String role = document.getString("role");
                                navigateToHome(role);
                            } else {
                                showToast("User data not found");
                            }
                        } else {
                            showToast("Failed to fetch user data: " + task.getException().getMessage());
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
