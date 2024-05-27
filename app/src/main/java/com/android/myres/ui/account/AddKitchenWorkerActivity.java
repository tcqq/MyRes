package com.android.myres.ui.account;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.adapter.SpinnerAdapter;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityAddKitchenWorkerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Form for creating a new kitchen worker account
 */
public class AddKitchenWorkerActivity extends BaseActivity {

    private ActivityAddKitchenWorkerBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_kitchen_worker);
        setActionBar(binding.toolbar);
        setActionBarTitle("Add Kitchen Worker");

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String[] genders = new String[] {"Male", "Female"};
        binding.workerGender.setAdapter(new SpinnerAdapter(this, genders));

        binding.addButton.setOnClickListener(v -> addKitchenWorker());
    }

    private void addKitchenWorker() {
        String firstName = binding.workerFirstName.getText().toString().trim();
        String lastName = binding.workerLastName.getText().toString().trim();
        String email = binding.workerEmail.getText().toString().trim();
        String password = binding.workerPassword.getText().toString().trim();
        String phone = binding.workerPhone.getText().toString().trim();
        String gender = binding.workerGender.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || gender.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Map<String, Object> worker = new HashMap<>();
                        worker.put("firstName", firstName);
                        worker.put("lastName", lastName);
                        worker.put("email", email);
                        worker.put("phone", phone);
                        worker.put("gender", gender);
                        worker.put("role", "Kitchen Worker");

                        db.collection("users").document(auth.getCurrentUser().getUid())
                                .set(worker)
                                .addOnSuccessListener(aVoid -> {
                                    showToast("Kitchen Worker Added Successfully");
                                    finish();
                                })
                                .addOnFailureListener(e -> showToast("Failed to Add Kitchen Worker"));
                    } else {
                        showToast("Failed to Create User");
                    }
                });
    }
}
