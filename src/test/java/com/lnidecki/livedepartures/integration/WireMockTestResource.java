package com.lnidecki.livedepartures.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.Map;

public class WireMockTestResource implements QuarkusTestResourceLifecycleManager {

    private static WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(8089)
                .usingFilesUnderDirectory("src/test/resources"));
        
        wireMockServer.start();

        return Map.of(
            "quarkus.rest-client.ttss-api.url", "http://localhost:8089",
            "quarkus.rest-client.ttss.url", "http://localhost:8089"
        );
    }

    @Override
    public void stop() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    public static WireMockServer getWireMock() {
        return wireMockServer;
    }
}