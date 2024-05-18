package com.android.myres.ui.order.data;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class Order {
    private String orderNumber;
    private String orderDetails;
    private String orderStatus;

    public Order(String orderNumber, String orderDetails, String orderStatus) {
        this.orderNumber = orderNumber;
        this.orderDetails = orderDetails;
        this.orderStatus = orderStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}