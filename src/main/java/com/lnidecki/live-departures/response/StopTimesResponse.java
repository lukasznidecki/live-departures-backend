package com.example.response;

import com.example.model.StopTime;
import java.util.List;

public class StopTimesResponse {
    public List<StopTime> currentStopTimes;

    public StopTimesResponse() {}

    public StopTimesResponse(List<StopTime> currentStopTimes) {
        this.currentStopTimes = currentStopTimes;
    }
}