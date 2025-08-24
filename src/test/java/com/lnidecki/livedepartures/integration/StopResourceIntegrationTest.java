package com.lnidecki.livedepartures.integration;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(WireMockTestResource.class)
public class StopResourceIntegrationTest {

    @Test
    public void testGetStops() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/stops\\?type=t"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            [
                                {
                                    "id": "3001",
                                    "name": "Rondo Mogilskie",
                                    "lat": 50.077500,
                                    "lon": 19.936389
                                },
                                {
                                    "id": "3002",
                                    "name": "Dworzec Główny Wschód",
                                    "lat": 50.066944,
                                    "lon": 19.945833
                                }
                            ]
                            """)));

        wireMock.stubFor(get(urlMatching("/stops\\?type=b"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            [
                                {
                                    "id": "1001",
                                    "name": "Plac Wszystkich Świętych",
                                    "lat": 50.064722,
                                    "lon": 19.936944
                                },
                                {
                                    "id": "1002",
                                    "name": "Teatr Bagatela",
                                    "lat": 50.063056,
                                    "lon": 19.937500
                                }
                            ]
                            """)));

        given()
                .when().get("/api/stops")
                .then()
                .statusCode(200)
                .body("stops", hasSize(4))
                .body("stops[0].stop_num", notNullValue())
                .body("stops[0].stop_name", notNullValue());
    }

    @Test
    public void testGetStopTimesById() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/services/passageInfo/stopPassages/stop\\?stop=1001&mode=departure"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "stopName": "Plac Wszystkich Świętych",
                                "stopShortName": "1001",
                                "actual": [
                                    {
                                        "actualTime": "14:25",
                                        "actualRelativeTime": 5,
                                        "direction": "Kurdwanów P+R",
                                        "mixedTime": "14:25",
                                        "patternText": "422",
                                        "plannedTime": "14:24",
                                        "routeId": 422,
                                        "status": "PREDICTED",
                                        "vehicleId": "BA123"
                                    }
                                ]
                            }
                            """)));

        wireMock.stubFor(get(urlMatching("/proxy_tram.php/services/passageInfo/stopPassages/stop\\?stop=1001&mode=departure"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "stopName": "Plac Wszystkich Świętych",
                                "stopShortName": "1001",
                                "actual": []
                            }
                            """)));

        given()
                .when().get("/api/stops/1001/current_stop_times")
                .then()
                .statusCode(200)
                .body("current_stop_times", hasSize(1))
                .body("current_stop_times[0].route_short_name", equalTo("422"))
                .body("current_stop_times[0].departure_timestamp", equalTo("14:25"));
    }


    @Test
    public void testHandleStopApiFailure() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/stops.*"))
                .willReturn(aResponse()
                        .withStatus(500)));

        given()
                .when().get("/api/stops")
                .then()
                .statusCode(500);
    }

    @Test
    public void testHandleStopPassageApiFailure() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching(".*stopPassages.*"))
                .willReturn(aResponse()
                        .withStatus(403)));

        given()
                .when().get("/api/stops/1001/current_stop_times")
                .then()
                .statusCode(200)
                .body("current_stop_times", hasSize(0));
    }

    @Test
    public void testGetStopTimesEmptyResponse() {
        WireMockServer wireMock = WireMockTestResource.getWireMock();

        wireMock.stubFor(get(urlMatching("/proxy_bus.php/services/passageInfo/stopPassages/stop\\?stop=9999&mode=departure"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "stopName": "Test Stop",
                                "stopShortName": "9999",
                                "actual": []
                            }
                            """)));

        wireMock.stubFor(get(urlMatching("/proxy_tram.php/services/passageInfo/stopPassages/stop\\?stop=9999&mode=departure"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "stopName": "Test Stop",
                                "stopShortName": "9999",
                                "actual": []
                            }
                            """)));

        given()
                .when().get("/api/stops/9999/current_stop_times")
                .then()
                .statusCode(200)
                .body("current_stop_times", hasSize(0));
    }
}