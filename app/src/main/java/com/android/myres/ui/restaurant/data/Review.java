package com.android.myres.ui.restaurant.data;

public class Review {
    private String name;
    private int rating;
    private String comment;
    private String contact;

    public Review(String name, int rating, String comment, String contact) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getContact() {
        return contact;
    }
}
