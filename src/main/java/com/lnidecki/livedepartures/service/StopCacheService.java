package com.lnidecki.livedepartures.service;

import com.lnidecki.livedepartures.client.TtssApiClient;
import com.lnidecki.livedepartures.dto.TtssStopDto;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class StopCacheService {

    @Inject
    @RestClient
    TtssApiClient ttssApiClient;

    private final Map<String, List<String>> stopNameToIds = new ConcurrentHashMap<>();

    @PostConstruct
    public void initializeCache() {
        loadStops();
    }

    private void loadStops() {
        Map<String, List<String>> tempCache = new HashMap<>();

        
        try {
            List<TtssStopDto> tramStops = ttssApiClient.getStops("t");
            for (TtssStopDto stop : tramStops) {
                tempCache.computeIfAbsent(stop.name, k -> new ArrayList<>()).add(stop.id);
            }
        } catch (Exception e) {
            System.out.println("Failed to load tram stops: " + e.getMessage());
        }

        
        try {
            List<TtssStopDto> busStops = ttssApiClient.getStops("b");
            for (TtssStopDto stop : busStops) {
                tempCache.computeIfAbsent(stop.name, k -> new ArrayList<>()).add(stop.id);
            }
        } catch (Exception e) {
            System.out.println("Failed to load bus stops: " + e.getMessage());
        }

        stopNameToIds.clear();
        stopNameToIds.putAll(tempCache);
    }

    public List<String> getStopIdsByName(String stopName) {
        return stopNameToIds.getOrDefault(stopName, new ArrayList<>());
    }

    public void refreshCache() {
        loadStops();
    }
}