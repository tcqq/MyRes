package com.android.myres.ui.restaurant.data;

public class OrderItem {
    private MenuItem menuItem;
    private String notes;
    private int quantity;

    public OrderItem(MenuItem menuItem, String notes, int quantity) {
        this.menuItem = menuItem;
        this.notes = notes;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }
}
