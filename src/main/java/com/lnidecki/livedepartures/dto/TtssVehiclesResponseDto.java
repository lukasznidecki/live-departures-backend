package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TtssVehiclesResponseDto {
    @JsonProperty("lastUpdate")
    public Long lastUpdate;
    
    @JsonProperty("vehicles")
    public List<TtssVehicleDto> vehicles;

    public TtssVehiclesResponseDto() {}
}