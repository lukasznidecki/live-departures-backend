package com.example.response;

import com.example.model.Stop;
import java.util.List;

public class StopsResponse {
    public List<Stop> stops;

    public StopsResponse() {}

    public StopsResponse(List<Stop> stops) {
        this.stops = stops;
    }
}