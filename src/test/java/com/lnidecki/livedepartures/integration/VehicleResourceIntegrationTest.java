package com.lnidecki.livedepartures.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@QuarkusTestResource(WireMockTestResource.class)
public class VehicleResourceIntegrationTest {

    @Test
    public void testGetActiveVehicles() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": [
                                    {
                                        "id": "BA123",
                                        "category": "bus",
                                        "name": "422 Kurdwanów",
                                        "tripId": "trip_123",
                                        "latitude": 180312288,
                                        "longitude": 71673125,
                                        "heading": 45.5,
                                        "color": "0xf89f05",
                                        "isDeleted": false
                                    }
                                ]
                            }
                            """)));

        wireMock.stubFor(get(urlMatching("/proxy_tram.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": [
                                    {
                                        "id": "3001",
                                        "category": "tram",
                                        "name": "21 Rondo Hipokratesa",
                                        "tripId": "tram_trip_456",
                                        "latitude": 180263242,
                                        "longitude": 72134153,
                                        "heading": 180.0,
                                        "color": "0xf89f05",
                                        "isDeleted": false
                                    }
                                ]
                            }
                            """)));

        given()
                .when().get("/api/vehicles/active/gtfs")
                .then()
                .statusCode(200)
                .body("vehicles", hasSize(2));
    }

    @Test
    public void testGetVehicles() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": [
                                    {
                                        "id": "BA456",
                                        "category": "bus",
                                        "name": "144 Salwator",
                                        "tripId": "trip_789",
                                        "latitude": 180200000,
                                        "longitude": 71800000,
                                        "heading": 90.0,
                                        "color": "0xf89f05",
                                        "isDeleted": false
                                    }
                                ]
                            }
                            """)));

        wireMock.stubFor(get(urlMatching("/proxy_tram.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": []
                            }
                            """)));

        given()
                .when().get("/api/vehicles")
                .then()
                .statusCode(200)
                .body("vehicles", hasSize(1));
    }

    @Test
    public void testFilterDeletedVehicles() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": [
                                    {
                                        "id": "BA123",
                                        "category": "bus",
                                        "name": "422 Kurdwanów",
                                        "tripId": "trip_123",
                                        "latitude": 180312288,
                                        "longitude": 71673125,
                                        "heading": 45.5,
                                        "color": "0xf89f05",
                                        "isDeleted": true
                                    },
                                    {
                                        "id": "BA456",
                                        "category": "bus",
                                        "name": "144 Salwator",
                                        "tripId": "trip_789",
                                        "latitude": 180200000,
                                        "longitude": 71800000,
                                        "heading": 90.0,
                                        "color": "0xf89f05",
                                        "isDeleted": false
                                    }
                                ]
                            }
                            """)));

        wireMock.stubFor(get(urlMatching("/proxy_tram.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": []
                            }
                            """)));

        given()
                .when().get("/api/vehicles/active/gtfs")
                .then()
                .statusCode(200)
                .body("vehicles", hasSize(1));
    }

    @Test
    public void testHandleVehicleApiFailure() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching(".*"))
                .willReturn(aResponse()
                        .withStatus(403)));

        given()
                .when().get("/api/vehicles/active/gtfs")
                .then()
                .statusCode(200)
                .body("vehicles", hasSize(0));
    }
}