package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Vehicle {
    @JsonProperty("category")
    public String category;
    @JsonProperty("floor")
    public String floor;
    @JsonProperty("full_kmk_id")
    public String fullKmkId;
    @JsonProperty("full_model_name")
    public String fullModelName;
    @JsonProperty("kmk_id")
    public String kmkId;
    @JsonProperty("short_model_name")
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