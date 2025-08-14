package com.example.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.example.model.StopTime;
import java.util.List;

public class StopTimesResponse {
    @JsonProperty("current_stop_times")
    public List<StopTime> currentStopTimes;

    public StopTimesResponse() {}

    public StopTimesResponse(List<StopTime> currentStopTimes) {
        this.currentStopTimes = currentStopTimes;
    }
}