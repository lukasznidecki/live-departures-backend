package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TtssStopDto {
    @JsonProperty("id")
    public String id;
    
    @JsonProperty("name")
    public String name;
    
    @JsonProperty("lat")
    public Double lat;
    
    @JsonProperty("lon")
    public Double lon;
    
    @JsonProperty("parent")
    public String parent;

    public TtssStopDto() {}
}