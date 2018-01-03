package com.example.guy.projectbagrut;

/**
 * Created by Guy on 1.1.2018.
 */

public class Business {

    private String name;
    private String address;
    protected String category;
    private double rating;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    public Business(String name, String address, String category, double rating)
    {

        this.name=name;
        this.address=address;
        this.category=category;
        this.rating=rating;
    }

}
