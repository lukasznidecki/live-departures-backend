package com.lnidecki.livedepartures.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StopTime {
    @JsonProperty("arrival") 
    Boolean arrival;
    
    @JsonProperty("block_id") 
    String blockId;
    
    @JsonProperty("category") 
    String category;
    
    @JsonProperty("departure_timestamp") 
    String departureTimestamp;
    
    @JsonProperty("hidden") 
    Boolean hidden;
    
    @JsonProperty("key") 
    String key;
    
    @JsonProperty("kmk_id") 
    String kmkId;
    
    @JsonProperty("live") 
    Boolean live;
    
    @JsonProperty("old") 
    Boolean old;
    
    @JsonProperty("planned_departure_time") 
    String plannedDepartureTime;
    
    @JsonProperty("planned_departure_timestamp") 
    String plannedDepartureTimestamp;
    
    @JsonProperty("predicted_delay") 
    Double predictedDelay;
    
    @JsonProperty("predicted_departure_timestamp") 
    String predictedDepartureTimestamp;
    
    @JsonProperty("route_short_name") 
    String routeShortName;
    
    @JsonProperty("service_id") 
    String serviceId;
    
    @JsonProperty("stop_num") 
    String stopNum;
    
    @JsonProperty("stopping") 
    Boolean stopping;
    
    @JsonProperty("trip_headsign") 
    String tripHeadsign;
    
    @JsonProperty("trip_id") 
    String tripId;
}