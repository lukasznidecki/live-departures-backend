package com.lnidecki.livedepartures.response;

import com.lnidecki.livedepartures.model.Stop;
import java.util.List;

public record StopsResponse(List<Stop> stops) {}