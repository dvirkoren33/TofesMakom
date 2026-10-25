package com.example.tofesmakom;

public class City {
    private String name;
    private String region;
    private int establishedYear;

    //  Constructor
    public City(String name, String region, int establishedYear) {
        this.name = name;
        this.region = region;
        this.establishedYear = establishedYear;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public int getEstablishedYear() {
        return establishedYear;
    }
}