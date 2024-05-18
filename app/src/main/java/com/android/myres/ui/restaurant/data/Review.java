package com.android.myres.ui.restaurant.data;

/**
 * @author Perry Lance
 * @since 2024-05-18 Created
 */
public class Review {
    private String name;
    private int rating;
    private String review;

    public Review(String name, int rating, String review) {
        this.name = name;
        this.rating = rating;
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
