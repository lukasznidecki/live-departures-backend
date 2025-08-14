package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TtssVehicleDto {
    @JsonProperty("id")
    public String id;
    
    @JsonProperty("category")
    public String category;
    
    @JsonProperty("name")
    public String name;
    
    @JsonProperty("tripId")
    public String tripId;
    
    @JsonProperty("latitude")
    public Long latitude;
    
    @JsonProperty("longitude")
    public Long longitude;
    
    @JsonProperty("heading")
    public Double heading;
    
    @JsonProperty("color")
    public String color;
    
    @JsonProperty("isDeleted")
    public Boolean isDeleted;

    public TtssVehicleDto() {}
}