package com.android.myres.ui.restaurant.data;

public class Restaurant {
    private String name;
    private String address;
    private String priceRange;
    private String foodType;
    private double rating;
    private String phone;
    private String imageUrl;
    private String hours;
    private String latitude;
    private String longitude;
    private double distance;
    private double minimumPrice; // 최소 가격 필드 추가

    // Default constructor for Firebase
    public Restaurant() {}

    // Constructor with parameters
    public Restaurant(String name, String address, String priceRange, String foodType, double rating, String phone, String imageUrl, String hours,   String latitude, String longitude) {
        this.name = name;
        this.address = address;
        this.priceRange = priceRange;
        this.foodType = foodType;
        this.rating = rating;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.hours = hours;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public String getFoodType() {
        return foodType;
    }

    public double getRating() {
        return rating;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getHours() {
        return hours;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getMinimumPrice() {
        if (priceRange == null || !priceRange.contains(" - ")) {
            return 0;
        }
        try {
            String minPriceStr = priceRange.split(" - ")[0].replace("$", "").trim();
            return Double.parseDouble(minPriceStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }
}
