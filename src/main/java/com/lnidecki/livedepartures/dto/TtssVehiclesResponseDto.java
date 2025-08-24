package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TtssVehiclesResponseDto(
    @JsonProperty("lastUpdate") Long lastUpdate,
    @JsonProperty("vehicles") List<TtssVehicleDto> vehicles
) {}