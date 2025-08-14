package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TtssPassageDto {
    @JsonProperty("actualTime")
    public String actualTime;
    
    @JsonProperty("actualRelativeTime")
    public Integer actualRelativeTime;
    
    @JsonProperty("direction")
    public String direction;
    
    @JsonProperty("mixedTime")
    public String mixedTime;
    
    @JsonProperty("patternText")
    public String patternText;
    
    @JsonProperty("plannedTime")
    public String plannedTime;
    
    @JsonProperty("routeId")
    public Long routeId;
    
    @JsonProperty("status")
    public String status;
    
    @JsonProperty("vehicleId")
    public String vehicleId;

    public TtssPassageDto() {}
}