package com.dicoding.projectcapstone.model;

public class Lokasi {
    private String name;
    private String distance;
    private String time;
    private int imageResource;
    private double rating;

    public Lokasi(String name, String distance, String time, int imageResource, double rating) {
        this.name = name;
        this.distance = distance;
        this.time = time;
        this.imageResource = imageResource;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }

    public int getImageResource() {
        return imageResource;
    }

    public double getRating() {
        return rating;
    }
}
