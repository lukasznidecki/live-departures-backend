package com.lnidecki.livedepartures.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record TtssStopPassagesDto(
    @JsonProperty("actual") List<TtssPassageDto> actual,
    @JsonProperty("stopName") String stopName,
    @JsonProperty("stopShortName") String stopShortName
) {}