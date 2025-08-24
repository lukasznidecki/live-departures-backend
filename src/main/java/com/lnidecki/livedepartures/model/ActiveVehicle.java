package com.lnidecki.livedepartures.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ActiveVehicle {
    @JsonProperty("bearing") 
    Double bearing;
    
    @JsonProperty("block_id") 
    String blockId;
    
    @JsonProperty("category") 
    String category;
    
    @JsonProperty("current_status") 
    Integer currentStatus;
    
    @JsonProperty("floor") 
    String floor;
    
    @JsonProperty("full_kmk_id") 
    String fullKmkId;
    
    @JsonProperty("key") 
    String key;
    
    @JsonProperty("kmk_id") 
    String kmkId;
    
    @JsonProperty("latitude") 
    Double latitude;
    
    @JsonProperty("longitude") 
    Double longitude;
    
    @JsonProperty("route_short_name") 
    String routeShortName;
    
    @JsonProperty("service_id") 
    String serviceId;
    
    @JsonProperty("shift") 
    String shift;
    
    @JsonProperty("source") 
    String source;
    
    @JsonProperty("stop_name") 
    String stopName;
    
    @JsonProperty("stop_num") 
    String stopNum;
    
    @JsonProperty("timestamp") 
    Long timestamp;
    
    @JsonProperty("trip_headsign") 
    String tripHeadsign;
    
    @JsonProperty("trip_id") 
    String tripId;
}