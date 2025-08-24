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

    @Test
    public void testGetVehiclesEmptyResponse() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": []
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
                .body("vehicles", hasSize(0));
    }

    @Test
    public void testGetActiveVehiclesEmptyResponse() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": []
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
                .body("vehicles", hasSize(0));
    }

    @Test
    public void testHandlePartialApiFailure() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(500)));

        wireMock.stubFor(get(urlMatching("/proxy_tram.php/geoserviceDispatcher/services/vehicleinfo/vehicles.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "lastUpdate": 1692014400,
                                "vehicles": [
                                    {
                                        "id": "3002",
                                        "category": "tram",
                                        "name": "4 Wzgórza Krzesławickie",
                                        "tripId": "tram_trip_789",
                                        "latitude": 180280000,
                                        "longitude": 72100000,
                                        "heading": 270.0,
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
                .body("vehicles", hasSize(1));
    }

    @Test
    public void testGetVehiclesWithNullCoordinates() {
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
                                        "id": "BA999",
                                        "category": "bus",
                                        "name": "999 Test Route",
                                        "tripId": "trip_999",
                                        "latitude": null,
                                        "longitude": null,
                                        "heading": 0.0,
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
    public void testGetVehiclesMixedData() {
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
                                        "id": "BA100",
                                        "category": "bus",
                                        "name": "100 Borek Fałęcki",
                                        "tripId": "trip_100",
                                        "latitude": 180100000,
                                        "longitude": 71600000,
                                        "heading": 120.0,
                                        "color": "0xf89f05",
                                        "isDeleted": false
                                    },
                                    {
                                        "id": "BA101",
                                        "category": "bus", 
                                        "name": "101",
                                        "tripId": "trip_101",
                                        "latitude": 180110000,
                                        "longitude": 71610000,
                                        "heading": 130.0,
                                        "color": "0xf89f05",
                                        "isDeleted": true
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
                                        "id": "3003",
                                        "category": "tram",
                                        "name": "7",
                                        "tripId": "tram_trip_7",
                                        "latitude": 180270000,
                                        "longitude": 72090000,
                                        "heading": 45.0,
                                        "color": "0xf89f05",
                                        "isDeleted": false
                                    }
                                ]
                            }
                            """)));

        given()
                .when().get("/api/vehicles")
                .then()
                .statusCode(200)
                .body("vehicles", hasSize(2));
    }
}