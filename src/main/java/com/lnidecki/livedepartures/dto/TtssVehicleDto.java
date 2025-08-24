package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TtssVehicleDto(
    @JsonProperty("id") String id,
    @JsonProperty("category") String category,
    @JsonProperty("name") String name,
    @JsonProperty("tripId") String tripId,
    @JsonProperty("latitude") Long latitude,
    @JsonProperty("longitude") Long longitude,
    @JsonProperty("heading") Double heading,
    @JsonProperty("color") String color,
    @JsonProperty("isDeleted") Boolean isDeleted
) {}