package com.android.myres.ui.restaurant;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityUpdateRestaurantBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Allows restaurant owners to update restaurant details
 */
public class UpdateRestaurantActivity extends BaseActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ActivityUpdateRestaurantBinding binding;
    private List<String> restaurantNames;
    private List<DocumentSnapshot> restaurantDocuments;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String selectedAddress;
    private String openTime;
    private String closeTime;
    private int selectedPosition = -1;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String selectedLatitude;
    private String selectedLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_restaurant);
        setActionBar(binding.toolbar);
        setActionBarTitle("Update Restaurant");

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("restaurant_images");

        restaurantNames = new ArrayList<>();
        restaurantDocuments = new ArrayList<>();

        loadRestaurants();
        binding.restaurantPriceRange.setValues(10f, 150f);
        binding.restaurantPriceRange.addOnChangeListener((slider, value, fromUser) -> updatePriceRangeDisplay());

        binding.updateButton.setOnClickListener(v -> updateRestaurant());

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
            public void onPlaceSelected(@NonNull Place place) {
                // Get info about the selected place
                selectedAddress = place.getAddress();
                selectedLatitude = place.getLatLng().latitude + "";
                selectedLongitude = place.getLatLng().longitude+ "";
                setAutocompleteFragmentText(autocompleteFragment, selectedAddress);
            }

            @Override
            public void onError(@NonNull Status status) {
                // Handle the error
                Toast.makeText(UpdateRestaurantActivity.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.restaurantHours.setOnClickListener(v -> showTimeRangePicker());

        binding.selectRestaurant.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            fillRestaurantData(position);
        });

        binding.restaurantImage.setOnClickListener(v -> openFileChooser());
    }

    private void loadRestaurants() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            showToast("User not logged in");
            return;
        }

        String userId = currentUser.getUid();

        db.collection("restaurants")
                .whereEqualTo("ownerId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        restaurantNames.clear();
                        restaurantDocuments.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            restaurantNames.add(document.getString("name"));
                            restaurantDocuments.add(document);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, restaurantNames);
                        binding.selectRestaurant.setAdapter(adapter);
                    } else {
                        Toast.makeText(this, "Failed to load restaurants", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fillRestaurantData(int position) {
        DocumentSnapshot document = restaurantDocuments.get(position);
        binding.restaurantName.setText(document.getString("name"));
        selectedAddress = document.getString("address"); // Store selected address
        selectedLatitude = document.getString("latitude");
        selectedLongitude = document.getString("longitude");
        binding.restaurantHours.setText(document.getString("hours"));
        binding.restaurantPhone.setText(document.getString("phone"));
        binding.restaurantFoodType.setText(document.getString("foodType"));
        String priceRange = document.getString("priceRange");
        if (priceRange != null && !priceRange.isEmpty()) {
            String[] priceRangeValues = priceRange.replace("$", "").split(" - ");
            List<Float> sliderValues = new ArrayList<>();
            sliderValues.add(Float.parseFloat(priceRangeValues[0]));
            sliderValues.add(Float.parseFloat(priceRangeValues[1]));
            binding.restaurantPriceRange.setValues(sliderValues);
            updatePriceRangeDisplay();
        }

        // Parse the hours to set the open and close time
        String hours = document.getString("hours");
        if (hours != null && hours.contains(" - ")) {
            String[] times = hours.split(" - ");
            openTime = times[0].trim();
            closeTime = times[1].trim();
        }

        // Set the initial address in the AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        setAutocompleteFragmentText(autocompleteFragment, document.getString("address"));
    }

    private void updatePriceRangeDisplay() {
        List<Float> values = binding.restaurantPriceRange.getValues();
        binding.priceRangeDisplay.setText("Selected Price Range: $" + values.get(0) + " - $" + values.get(1));
    }

    private void updateRestaurant() {
        String restaurantName = binding.restaurantName.getText().toString().trim();
        String restaurantHours = openTime + " - " + closeTime;
        String restaurantPhone = binding.restaurantPhone.getText().toString().trim();
        String restaurantFoodType = binding.restaurantFoodType.getText().toString().trim();
        List<Float> priceRangeValues = binding.restaurantPriceRange.getValues();
        String priceRange = "$" + priceRangeValues.get(0) + " - $" + priceRangeValues.get(1);

        if (restaurantName.isEmpty() || selectedAddress == null || openTime == null || closeTime == null || restaurantPhone.isEmpty() || restaurantFoodType.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            if (selectedPosition < 0 || selectedPosition >= restaurantDocuments.size()) {
                Toast.makeText(this, "Please select a restaurant", Toast.LENGTH_SHORT).show();
                return;
            }

            DocumentSnapshot selectedDocument = restaurantDocuments.get(selectedPosition);

            if (imageUri != null) {
                StorageReference fileReference = storageRef.child(System.currentTimeMillis() + ".jpg");
                fileReference.putFile(imageUri)
                        .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            updateRestaurantInDatabase(selectedDocument, restaurantName, restaurantHours, restaurantPhone, restaurantFoodType, priceRange, selectedLatitude, selectedLongitude, imageUrl);
                        }))
                        .addOnFailureListener(e -> Toast.makeText(UpdateRestaurantActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
            } else {
                updateRestaurantInDatabase(selectedDocument, restaurantName, restaurantHours, restaurantPhone, restaurantFoodType, priceRange, selectedLatitude, selectedLongitude, null);
            }
        }
    }

    private void updateRestaurantInDatabase(DocumentSnapshot document, String name, String hours, String phone, String foodType, String priceRange, String latitude, String longitude, String imageUrl) {
        document.getReference().update("name", name,
                        "address", selectedAddress,
                        "hours", hours,
                        "phone", phone,
                        "foodType", foodType,
                        "priceRange", priceRange,
                        "latitude", latitude,
                        "longitude", longitude,
                        "imageUrl", imageUrl != null ? imageUrl : document.getString("imageUrl"))
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Restaurant Updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to update restaurant", Toast.LENGTH_SHORT).show());
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

    private void setAutocompleteFragmentText(AutocompleteSupportFragment fragment, String text) {
        // Use reflection to get the mAutocompleteTextView field
        try {
            Field field = AutocompleteSupportFragment.class.getDeclaredField("zzap");
            field.setAccessible(true);
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) field.get(fragment);

            // Set the text in the AutoCompleteTextView
            autoCompleteTextView.setText(text);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.restaurantImage.setImageURI(imageUri);
        }
    }
}
