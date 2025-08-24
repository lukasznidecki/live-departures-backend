package com.lnidecki.livedepartures.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Vehicle {
    @JsonProperty("category") 
    String category;
    
    @JsonProperty("floor") 
    String floor;
    
    @JsonProperty("full_kmk_id") 
    String fullKmkId;
    
    @JsonProperty("full_model_name") 
    String fullModelName;
    
    @JsonProperty("kmk_id") 
    String kmkId;
    
    @JsonProperty("short_model_name") 
    String shortModelName;
}