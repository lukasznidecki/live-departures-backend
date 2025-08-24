package com.lnidecki.livedepartures.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Stop {
    @JsonProperty("bus") 
    Boolean bus;
    
    @JsonProperty("stop_lat") 
    Double stopLat;
    
    @JsonProperty("stop_lon") 
    Double stopLon;
    
    @JsonProperty("stop_name") 
    String stopName;
    
    @JsonProperty("stop_num") 
    String stopNum;
    
    @JsonProperty("tram") 
    Boolean tram;
}