package com.android.myres.ui.restaurant;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityAddRestaurantBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Form for adding restaurant details and building menu
 */
public class AddRestaurantActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ActivityAddRestaurantBinding binding;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri imageUri;
    private String selectedAddress;
    private String openTime;
    private String closeTime;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_restaurant);
        setActionBar(binding.toolbar);
        setActionBarTitle("Add Restaurant");

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        binding.restaurantPriceRange.setValues(10f, 150f);
        binding.restaurantPriceRange.addOnChangeListener((slider, value, fromUser) -> updatePriceRangeDisplay());

        binding.saveButton.setOnClickListener(v -> saveRestaurant());

        binding.selectImageButton.setOnClickListener(v -> openFileChooser());

        // Initialize the Google Places API
        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));

        // Initialize the AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // Get info about the selected place
                selectedAddress = place.getAddress();
                if (place.getLatLng() != null) {
                    latitude = String.valueOf(place.getLatLng().latitude);
                    longitude = String.valueOf(place.getLatLng().longitude);
                }
                // binding.restaurantAddress.setText(selectedAddress); // Set the address in the TextView
            }

            @Override
            public void onError(Status status) {
                // Handle the error
                Toast.makeText(AddRestaurantActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.restaurantHours.setOnClickListener(v -> showTimeRangePicker());
    }

    private void updatePriceRangeDisplay() {
        List<Float> values = binding.restaurantPriceRange.getValues();
        if (values.size() >= 2) {
            String priceRangeText = String.format("Selected Price Range: %.0f$ - %.0f$", values.get(0), values.get(1));
            binding.priceRangeDisplay.setText(priceRangeText); // 예: 텍스트 뷰에 설정
        } else {
            String priceRangeText = "Selected Price Range: 10$ - 150$";
            binding.priceRangeDisplay.setText(priceRangeText); // 기본 값 설정
        }
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(binding.restaurantImage);
        }
    }

    private void saveRestaurant() {
        String restaurantName = binding.restaurantName.getText().toString().trim();
        String restaurantHours = openTime + " - " + closeTime;
        String restaurantPhone = binding.restaurantPhone.getText().toString().trim();
        String restaurantFoodType = binding.restaurantFoodType.getText().toString().trim();
        List<Float> priceRangeValues = binding.restaurantPriceRange.getValues();
        String restaurantPriceRange = String.format("%.0f$ - %.0f$", priceRangeValues.get(0), priceRangeValues.get(1));

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            showToast("User not logged in");
            return;
        }

        String userId = currentUser.getUid();

        if (restaurantName.isEmpty() || selectedAddress == null || openTime == null || closeTime == null ||
                restaurantPhone.isEmpty() || restaurantFoodType.isEmpty() || priceRangeValues.isEmpty() || latitude == null || longitude == null) {
            showToast("Please fill in all fields");
        } else {
            if (imageUri != null) {
                uploadImageToFirebase(imageUri, imageUrl -> {
                    Map<String, Object> restaurant = new HashMap<>();
                    restaurant.put("name", restaurantName);
                    restaurant.put("address", selectedAddress);
                    restaurant.put("hours", restaurantHours);
                    restaurant.put("phone", restaurantPhone);
                    restaurant.put("foodType", restaurantFoodType);
                    restaurant.put("priceRange", restaurantPriceRange);
                    restaurant.put("rating", 0);
                    restaurant.put("ownerId", userId); // Store the user ID
                    restaurant.put("latitude", latitude);
                    restaurant.put("longitude", longitude);
                    restaurant.put("imageUrl", imageUrl);

                    db.collection("restaurants")
                            .add(restaurant)
                            .addOnSuccessListener(documentReference -> {
                                showToast("Restaurant Saved Successfully");
                                finish();
                            })
                            .addOnFailureListener(e -> showToast("Failed to Save Restaurant"));
                });
            } else {
                Map<String, Object> restaurant = new HashMap<>();
                restaurant.put("name", restaurantName);
                restaurant.put("address", selectedAddress);
                restaurant.put("hours", restaurantHours);
                restaurant.put("phone", restaurantPhone);
                restaurant.put("foodType", restaurantFoodType);
                restaurant.put("priceRange", restaurantPriceRange);
                restaurant.put("rating", 0);
                restaurant.put("ownerId", userId); // Store the user ID
                restaurant.put("latitude", latitude);
                restaurant.put("longitude", longitude);

                db.collection("restaurants")
                        .add(restaurant)
                        .addOnSuccessListener(documentReference -> {
                            showToast("Restaurant Saved Successfully");
                            finish();
                        })
                        .addOnFailureListener(e -> showToast("Failed to Save Restaurant"));
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri, OnImageUploadSuccessListener listener) {
        StorageReference fileReference = storageReference.child("restaurants/" + System.currentTimeMillis() + ".jpg");
        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> listener.onSuccess(uri.toString())))
                .addOnFailureListener(e -> showToast("Image Upload Failed"));
    }

    private void showTimeRangePicker() {
        // Initialize time to the current time
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Open time picker dialog
        TimePickerDialog openTimePicker = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            openTime = String.format("%02d:%02d", hourOfDay, minute1);
            // Close time picker dialog
            TimePickerDialog closeTimePicker = new TimePickerDialog(this, (view1, hourOfDay1, minute2) -> {
                closeTime = String.format("%02d:%02d", hourOfDay1, minute2);
                binding.restaurantHours.setText(String.format("%s - %s", openTime, closeTime));
            }, hour, minute, DateFormat.is24HourFormat(this));
            closeTimePicker.setTitle("Select Close Time");
            closeTimePicker.show();
        }, hour, minute, DateFormat.is24HourFormat(this));
        openTimePicker.setTitle("Select Open Time");
        openTimePicker.show();
    }

    private interface OnImageUploadSuccessListener {
        void onSuccess(String imageUrl);
    }
}
