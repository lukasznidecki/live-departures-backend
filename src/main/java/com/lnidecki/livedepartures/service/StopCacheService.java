package com.lnidecki.livedepartures.service;

import com.lnidecki.livedepartures.client.TtssApiClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        var tempCache = List.of("t", "b").stream()
            .collect(Collectors.toMap(
                type -> type,
                type -> loadStopsByType(type),
                (existing, replacement) -> existing
            ));

        var nameToIdsMap = tempCache.values().stream()
            .flatMap(List::stream)
            .collect(Collectors.groupingBy(
                stop -> stop.name(),
                Collectors.mapping(stop -> stop.id(), Collectors.toList())
            ));

        stopNameToIds.clear();
        stopNameToIds.putAll(nameToIdsMap);
    }

    private List<com.lnidecki.livedepartures.dto.TtssStopDto> loadStopsByType(String type) {
        try {
            return ttssApiClient.getStops(type);
        } catch (Exception e) {
            System.out.println("Failed to load " + type + " stops: " + e.getMessage());
            return List.of();
        }
    }

    public List<String> getStopIdsByName(String stopName) {
        return stopNameToIds.getOrDefault(stopName, List.of());
    }

    public void refreshCache() {
        loadStops();
    }
}