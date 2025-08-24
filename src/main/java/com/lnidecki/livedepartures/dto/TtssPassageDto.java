package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TtssPassageDto(
    @JsonProperty("actualTime") String actualTime,
    @JsonProperty("actualRelativeTime") Integer actualRelativeTime,
    @JsonProperty("direction") String direction,
    @JsonProperty("mixedTime") String mixedTime,
    @JsonProperty("patternText") String patternText,
    @JsonProperty("plannedTime") String plannedTime,
    @JsonProperty("routeId") Long routeId,
    @JsonProperty("status") String status,
    @JsonProperty("vehicleId") String vehicleId
) {}