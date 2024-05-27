package com.android.myres.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityClientHomeBinding;
import com.android.myres.ui.restaurant.adapter.RestaurantAdapter;
import com.android.myres.ui.restaurant.data.Restaurant;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientHomeActivity extends BaseActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private ActivityClientHomeBinding binding;
    private FirebaseFirestore db;
    private List<Restaurant> restaurantList;
    private RestaurantAdapter adapter;
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_client_home);
        setActionBar(binding.toolbar);
        setActionBarTitle("Client Home");

        db = FirebaseFirestore.getInstance();
        restaurantList = new ArrayList<>();
        adapter = new RestaurantAdapter(restaurantList, this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding.recyclerViewRestaurants.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewRestaurants.setAdapter(adapter);

        String[] sorts = new String[]{"Sort by Distance", "Sort by Price", "Sort by Rating"};
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sorts);
        binding.sortBy.setAdapter(sortAdapter);

        binding.sortBy.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    if (currentLocation != null) {
                        sortRestaurantsByDistance();
                    } else {
                        requestLocationPermission();
                    }
                    break;
                case 1:
                    sortRestaurantsByPrice();
                    break;
                case 2:
                    sortRestaurantsByRating();
                    break;
            }
        });

        binding.keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRestaurants(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        requestLocationPermission();
        loadRestaurants();
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        currentLocation = location;
                        sortRestaurantsByDistance();
                    }
                });
    }

    private void sortRestaurantsByDistance() {
        if (currentLocation == null) {
            return;
        }

        for (Restaurant restaurant : restaurantList) {
            float[] results = new float[1];
            Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    Double.parseDouble(restaurant.getLatitude()), Double.parseDouble(restaurant.getLongitude()), results);
            restaurant.setDistance(results[0]);
        }

        Collections.sort(restaurantList, Comparator.comparingDouble(Restaurant::getDistance));
        adapter.notifyDataSetChanged();
    }

    private void sortRestaurantsByPrice() {
        for (Restaurant restaurant : restaurantList) {
            double minPrice = restaurant.getMinimumPrice();
            restaurant.setMinimumPrice(minPrice);
        }

        Collections.sort(restaurantList, Comparator.comparingDouble(Restaurant::getMinimumPrice));
        adapter.notifyDataSetChanged();
    }

    private void sortRestaurantsByRating() {
        Collections.sort(restaurantList, Comparator.comparingDouble(Restaurant::getRating));
        adapter.notifyDataSetChanged();
    }

    private void loadRestaurants() {
        db.collection("restaurants")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Restaurant restaurant = document.toObject(Restaurant.class);
                            restaurantList.add(restaurant);
                        }
                        if (currentLocation != null) {
                            sortRestaurantsByDistance();
                        }
                        adapter.updateList(restaurantList);
                    } else {
                        Toast.makeText(ClientHomeActivity.this, "Error getting documents.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void filterRestaurants(String keyword) {
        List<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant restaurant : restaurantList) {
            if (restaurant.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(restaurant);
            }
        }
        adapter.updateList(filteredList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
