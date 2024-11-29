package com.dicoding.projectcapstone.kategori;

public class Kategori {
    private int imageResource;
    private String name;
    private double rating;
    private String distance;
    private String price;
    private boolean isOpen;


    public Kategori(int imageResource, String name, double rating, String distance, String price, boolean isOpen) {
        this.imageResource = imageResource;
        this.name = name;
        this.rating = rating;
        this.distance = distance;
        this.price = price;
        this.isOpen = isOpen;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getDistance() {
        return distance;
    }

    public String getPrice() {
        return price;
    }

    public boolean isOpen() {
        return isOpen;
    }
}

