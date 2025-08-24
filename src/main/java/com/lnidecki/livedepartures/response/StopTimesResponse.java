package com.lnidecki.livedepartures.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lnidecki.livedepartures.model.StopTime;
import java.util.List;

public record StopTimesResponse(
    @JsonProperty("current_stop_times") List<StopTime> currentStopTimes
) {}