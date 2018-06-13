package com.example.guy.projectbagrut;

/**
 * Created by Guy on 1.1.2018.
 */

public class Business {

    //Yellow

    private String name;
    private String address;
    protected String category;
    private double rating;
    private double lng;
    private double lat;

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

    public Business(String name, String address, String category, double rating, double lng, double lat)
    {

        this.name=name;
        this.address=address;
        this.category=category;
        this.rating=rating;
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
