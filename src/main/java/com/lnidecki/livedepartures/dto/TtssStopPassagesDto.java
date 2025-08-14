package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TtssStopPassagesDto {
    @JsonProperty("actual")
    public List<TtssPassageDto> actual;
    
    @JsonProperty("stopName")
    public String stopName;
    
    @JsonProperty("stopShortName")
    public String stopShortName;

    public TtssStopPassagesDto() {}
}