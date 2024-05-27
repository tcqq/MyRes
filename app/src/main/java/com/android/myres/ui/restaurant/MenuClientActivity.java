package com.android.myres.ui.restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.myres.R;
import com.android.myres.common.base.BaseActivity;
import com.android.myres.databinding.ActivityMenuBinding;
import com.android.myres.ui.restaurant.adapter.MenuAdapter;
import com.android.myres.ui.restaurant.adapter.MenuOldAdapter;
import com.android.myres.ui.restaurant.data.MenuItem;
import com.android.myres.ui.restaurant.data.OrderItem;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays menu items, allows adding notes and placing orders
 */
public class MenuClientActivity extends BaseActivity {

    private ActivityMenuBinding binding;
    private FirebaseFirestore db;
    private List<MenuItem> menuItems;
    private MenuAdapter adapter;
    private List<OrderItem> currentOrder;
    private double totalOrderPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        setActionBar(binding.toolbar);
        setActionBarTitle("Menu");

        db = FirebaseFirestore.getInstance();
        menuItems = new ArrayList<>();
        currentOrder = new ArrayList<>();
        totalOrderPrice = 0.0;

        adapter = new MenuAdapter(menuItems, this::showAddToOrderDialog);

        binding.recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMenu.setAdapter(adapter);

        String restaurantId = getIntent().getStringExtra("restaurantId");

        loadMenuItems(restaurantId);

        binding.buttonPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void loadMenuItems(String restaurantId) {
        db.collection("restaurants")
                .document(restaurantId)
                .collection("menuItems")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        menuItems.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String description = document.getString("description");
                            double price = document.getDouble("price");
                            String imageUrl = document.getString("imageUrl");
                            menuItems.add(new MenuItem(title, description, price, imageUrl));
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to load menu items", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showAddToOrderDialog(MenuItem menuItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_to_order, null);
        builder.setView(dialogView);

        EditText editTextNotes = dialogView.findViewById(R.id.editTextNotes);
        EditText editTextQuantity = dialogView.findViewById(R.id.editTextQuantity);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String notes = editTextNotes.getText().toString();
            int quantity = Integer.parseInt(editTextQuantity.getText().toString());
            addToOrder(new OrderItem(menuItem, notes, quantity));
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void addToOrder(OrderItem orderItem) {
        currentOrder.add(orderItem);
        totalOrderPrice += orderItem.getTotalPrice();
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        binding.textViewTotalPrice.setText("Total Price: $" + String.format("%.2f", totalOrderPrice));
    }

    private void placeOrder() {
        // Implement order placing logic here
        Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show();
    }
}
