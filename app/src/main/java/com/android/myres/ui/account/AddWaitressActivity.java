package com.android.myres.ui.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityAddWaitressBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Form for creating a new waitress account
 */
public class AddWaitressActivity extends BaseActivity {

    private ActivityAddWaitressBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_waitress);
        setActionBar(binding.toolbar);
        setActionBarTitle("Add Waitress");

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String[] genders = new String[] {"Male", "Female"};
        binding.waitressGender.setAdapter(new SpinnerAdapter(this, genders));

        binding.addButton.setOnClickListener(v -> addWaitress());
    }

    private void addWaitress() {
        String firstName = binding.waitressFirstName.getText().toString().trim();
        String lastName = binding.waitressLastName.getText().toString().trim();
        String email = binding.waitressEmail.getText().toString().trim();
        String password = binding.waitressPassword.getText().toString().trim();
        String phone = binding.waitressPhone.getText().toString().trim();
        String gender = binding.waitressGender.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || gender.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> waitress = new HashMap<>();
                        waitress.put("firstName", firstName);
                        waitress.put("lastName", lastName);
                        waitress.put("email", email);
                        waitress.put("phone", phone);
                        waitress.put("gender", gender);
                        waitress.put("role", "Waitress");

                        db.collection("users").document(auth.getCurrentUser().getUid())
                                .set(waitress)
                                .addOnSuccessListener(aVoid -> {
                                    showToast("Waitress Added Successfully");
                                    finish();
                                })
                                .addOnFailureListener(e -> showToast("Failed to Add Waitress"));
                    } else {
                        showToast("Failed to Create User");
                    }
                });
    }
}
