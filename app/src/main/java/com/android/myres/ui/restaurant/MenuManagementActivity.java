package com.android.myres.ui.restaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityMenuManagementBinding;
import com.android.myres.ui.restaurant.adapter.DishAdapter;
import com.android.myres.ui.restaurant.data.Dish;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Allows adding, updating, or deleting menu items
 */
public class MenuManagementActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityMenuManagementBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private DishAdapter adapter;
    private List<Dish> dishList;
    private Dish selectedDish;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_management);
        setActionBar(binding.toolbar);
        setActionBarTitle("Menu Management");

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("dish_images");
        dishList = new ArrayList<>();
        adapter = new DishAdapter(dishList, this);

        binding.dishRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.dishRecyclerView.setAdapter(adapter);

        binding.addDishButton.setOnClickListener(v -> addOrUpdateDish());
        binding.selectImageButton.setOnClickListener(v -> openFileChooser());

        // Initialize restaurant list for selection
        String[] restaurantIds = new String[]{"Restaurant 1", "Restaurant 2", "Restaurant 3"};
        ArrayAdapter<String> restaurantAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, restaurantIds);
        binding.selectRestaurant.setAdapter(restaurantAdapter);

        fetchDishes();
    }

    private void addOrUpdateDish() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            showToast("User not logged in");
            return;
        }

        String userId = currentUser.getUid();
        String restaurantId = binding.selectRestaurant.getText().toString().trim();
        String title = binding.dishTitle.getText().toString().trim();
        String price = binding.dishPrice.getText().toString().trim();
        String description = binding.dishDescription.getText().toString().trim();
        String ingredients = binding.dishIngredients.getText().toString().trim();
        boolean isActive = binding.dishActive.isChecked();

        if (title.isEmpty() || price.isEmpty() || description.isEmpty() || ingredients.isEmpty() || restaurantId.isEmpty()) {
            showToast("Please fill in all fields");
            return;
        }

        if (imageUri == null) {
            showToast("Please select an image");
            return;
        }

        Map<String, Object> dish = new HashMap<>();
        dish.put("restaurantId", restaurantId);
        dish.put("title", title);
        dish.put("price", price);
        dish.put("description", description);
        dish.put("ingredients", ingredients);
        dish.put("active", isActive);
        dish.put("ownerId", userId);

        if (selectedDish == null) {
            // Add new dish
            uploadImageAndAddDish(dish);
        } else {
            // Update existing dish
            uploadImageAndUpdateDish(dish);
        }
    }

    private void uploadImageAndAddDish(Map<String, Object> dish) {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
            dish.put("imageUrl", uri.toString());
            db.collection("dishes").add(dish).addOnSuccessListener(documentReference -> {
                showToast("Dish Added Successfully");
                fetchDishes();
                clearFields();
            }).addOnFailureListener(e -> showToast("Failed to Add Dish"));
        })).addOnFailureListener(e -> showToast("Image Upload Failed"));
    }

    private void uploadImageAndUpdateDish(Map<String, Object> dish) {
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
            dish.put("imageUrl", uri.toString());
            db.collection("dishes").document(selectedDish.getId()).update(dish).addOnSuccessListener(aVoid -> {
                showToast("Dish Updated Successfully");
                fetchDishes();
                clearFields();
            }).addOnFailureListener(e -> showToast("Failed to Update Dish"));
        })).addOnFailureListener(e -> showToast("Image Upload Failed"));
    }

    public void onDishClicked(Dish dish) {
        selectedDish = dish;
        binding.dishTitle.setText(dish.getTitle());
        binding.dishPrice.setText(dish.getPrice());
        binding.dishDescription.setText(dish.getDescription());
        binding.dishIngredients.setText(dish.getIngredients());
        binding.dishActive.setChecked(dish.isActive());
        binding.addDishButton.setText("Update Dish");
    }

    public void deleteDish(Dish dish) {
        db.collection("dishes").document(dish.getId()).delete().addOnSuccessListener(aVoid -> {
            showToast("Dish Deleted Successfully");
            fetchDishes();
        }).addOnFailureListener(e -> showToast("Failed to Delete Dish"));
    }

    private void fetchDishes() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            showToast("User not logged in");
            return;
        }

        String userId = currentUser.getUid();

        db.collection("dishes").whereEqualTo("ownerId", userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                dishList.clear();
                for (DocumentSnapshot document : task.getResult()) {
                    Dish dish = document.toObject(Dish.class);
                    if (dish != null) {
                        dish.setId(document.getId());
                        dishList.add(dish);
                    }
                }
                adapter.updateDishes(dishList);
            } else {
                showToast("Failed to fetch dishes");
            }
        });
    }

    private void clearFields() {
        selectedDish = null;
        binding.dishTitle.setText("");
        binding.dishPrice.setText("");
        binding.dishDescription.setText("");
        binding.dishIngredients.setText("");
        binding.dishActive.setChecked(false);
        binding.dishImage.setImageResource(R.drawable.ic_launcher_background);
        binding.addDishButton.setText("Add Dish");
        imageUri = null;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                binding.dishImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
