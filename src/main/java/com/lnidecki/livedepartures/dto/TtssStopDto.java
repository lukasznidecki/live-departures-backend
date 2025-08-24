package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TtssStopDto(
    @JsonProperty("id") String id,
    @JsonProperty("name") String name,
    @JsonProperty("lat") Double lat,
    @JsonProperty("lon") Double lon,
    @JsonProperty("parent") String parent
) {}