package com.example.model;

public class Vehicle {
    public String category;
    public String floor;
    public String fullKmkId;
    public String fullModelName;
    public String kmkId;
    public String shortModelName;

    public Vehicle() {}

    public Vehicle(String category, String floor, String fullKmkId, String fullModelName, 
                   String kmkId, String shortModelName) {
        this.category = category;
        this.floor = floor;
        this.fullKmkId = fullKmkId;
        this.fullModelName = fullModelName;
        this.kmkId = kmkId;
        this.shortModelName = shortModelName;
    }
}