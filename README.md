# Live Departures Backend

## 🚌 Enhanced Krakow TTSS API

An improved and optimized backend for Krakow's public transport system, providing real-time departures and stop information through a modern REST API.

**Try the app: [https://live-departures.pages.dev/](https://live-departures.pages.dev/)**

<p align="center">
  <img src="demo1.gif" height="75%"  />
  &nbsp;
  &nbsp;
  &nbsp;
  &nbsp;
  &nbsp;
  &nbsp;
  <img src="demo2.gif" height="75%"  />
</p>

## 🌐 Related Projects

- **Frontend**: [live-departures](https://github.com/lukasznidecki/live-departures) - PWA Angular App
- **Infrastructure**: [live-departures-infra](https://github.com/lukasznidecki/live-departures-infra) - Deployment and
  infrastructure configuration

## 🚀 Features

- **Enhanced TTSS Integration** - Direct integration with Krakow's official TTSS API
- **Smart Stop Name Resolution** - Query departures by stop name (e.g., "Górka Narodowa P+R")
- **Live Vehicle Tracking** - Real-time GPS positions of all buses and trams from TTSS
- **Intelligent Caching** - Startup caching of all stops for optimal performance
- **Real-time vehicle tracking** - GPS coordinates, heading, and route information
- **Stop information** - Bus and tram stops with coordinates
- **Departure times** - Current and predicted departure/arrival times from multiple platforms
- **Vehicle fleet data** - Complete information about all vehicles
- **OpenAPI/Swagger documentation** - Interactive API documentation

## 🛠 Tech Stack

- **Java 21** - Modern Java with latest features
- **Quarkus 3.6** - Supersonic subatomic Java framework
- **RESTEasy Reactive** - High-performance REST endpoints
- **Jackson** - JSON serialization/deserialization
- **SmallRye OpenAPI** - API documentation and Swagger UI
- **Maven** - Dependency management and build tool
- **Helm** - Kubernetes deployment and package management

## 📋 API Endpoints

| Method | Endpoint                               | Description                                     |
|--------|----------------------------------------|-------------------------------------------------|
| `GET`  | `/api/vehicles`                        | Get all vehicles in the fleet                   |
| `GET`  | `/api/vehicles/active/gtfs`            | Get active vehicles with real-time GPS data     |
| `GET`  | `/api/stops`                           | Get all bus and tram stops                      |
| `GET`  | `/api/stops/{stop}/current_stop_times` | Get departures by stop ID or stop name         |

## 🎯 Enhanced TTSS Features

### Smart Stop Name Query
Query departures using human-readable stop names instead of cryptic IDs:

```bash
# Traditional approach (still supported)
curl http://localhost:8080/api/stops/01/current_stop_times

# Enhanced approach - use stop names directly!
curl http://localhost:8080/api/stops/Górka%20Narodowa%20P%2BR/current_stop_times
curl http://localhost:8080/api/stops/Elektromontaż/current_stop_times
```

### Intelligent Stop Resolution
- Automatically detects if input is a stop name or ID
- Finds all stop platforms matching the name
- Aggregates departures from all matching platforms
- Handles Polish characters and special symbols

### Performance Optimizations
- **Startup Caching**: All stops cached at application start
- **Efficient API Calls**: Direct TTSS integration with optimized requests
- **Error Resilience**: Graceful handling of TTSS API failures

### Live Vehicle Tracking
- **Real-time GPS Data**: Direct integration with TTSS vehicle positioning system
- **Route-based Coloring**: Vehicles colored by their routes for easy identification
- **Heading Information**: Vehicle direction and movement data
- **Multi-modal Support**: Both buses and trams tracked simultaneously

## 🚦 Getting Started

### Prerequisites

- Java 21 or later
- Maven 3.8.1 or later

### Development

```bash
# Clone the repository
git clone <repository-url>
cd live-departures-backend

# Run in development mode (with live reload)
./mvnw quarkus:dev
```

The application will start on `http://localhost:8080`

### API Documentation

- **Swagger UI**: http://localhost:8080/swagger-ui
- **OpenAPI Spec**: http://localhost:8080/q/openapi

### Building

```bash
# Create JAR package
./mvnw clean package

# Run the JAR
java -jar target/quarkus-app/quarkus-run.jar
```

### Native Build

```bash
# Build native executable
./mvnw package -Pnative

# Run native executable
./target/live-departures-backend-1.0.0-SNAPSHOT-runner
```

## 🧪 Testing

```bash
# Run unit tests
./mvnw test

# Run with coverage
./mvnw verify
```

## 🔧 Configuration

Configuration is handled through `src/main/resources/application.properties`:

```properties
# Server configuration
quarkus.http.port=8080
quarkus.http.cors=true
quarkus.http.cors.origins=*
# OpenAPI configuration
quarkus.smallrye-openapi.info-title=Live Departures API
quarkus.smallrye-openapi.info-version=1.0.0
quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.path=/swagger-ui
```

## 🚀 Deployment

This backend is deployed using the infrastructure defined
in [live-departures-infra](https://github.com/lukasznidecki/live-departures-infra).

### Production Deployment

The application is automatically deployed using the infrastructure configuration. See
the [live-departures-infra](https://github.com/lukasznidecki/live-departures-infra) repository for deployment details
and environment setup.

### Manual Deployment

```bash
# Build production JAR
./mvnw clean package -DskipTests

# Deploy JAR file
java -jar target/quarkus-app/quarkus-run.jar
```

### GitHub Actions

The project includes CI/CD workflows:

- **CI Pipeline**: Runs tests and builds on every push/PR
- **Release Pipeline**: Creates GitHub releases when tags are pushed

```bash
# Create a release
git tag v1.0.0
git push origin v1.0.0
```

## 📚 API Examples

### Get Active Vehicles - Live TTSS Data! 🚀

```bash
# Real-time positions of all buses and trams in Krakow
curl http://localhost:8080/api/vehicles/active/gtfs
```

**Response includes:**
- GPS coordinates (lat/lng)
- Vehicle heading/direction
- Route information
- Vehicle IDs and trip data
- Route-based color coding

### Get Stop Information

```bash
curl http://localhost:8080/api/stops
```

### Get Stop Times - Enhanced!

```bash
# By stop ID (traditional)
curl http://localhost:8080/api/stops/01/current_stop_times

# By stop name (NEW! 🚀)
curl "http://localhost:8080/api/stops/Główny Wschód/current_stop_times"
curl "http://localhost:8080/api/stops/Plac Centralny im. Ronalda Reagana/current_stop_times"
curl "http://localhost:8080/api/stops/Dworzec Główny Tunel/current_stop_times"

# Works with Polish characters and special symbols
curl "http://localhost:8080/api/stops/Kraków Główny/current_stop_times"
```

## 🔧 TTSS Integration Details

This backend directly integrates with Krakow's official TTSS (Transport Telematics System) APIs:

### Stop & Departure APIs
- **Stops API**: `https://api.ttss.pl/stops/?type=t` (trams) and `?type=b` (buses)  
- **Departures API**: `https://ttss.pl/proxy_bus.php/services/passageInfo/stopPassages/stop` (buses)
- **Departures API**: `https://ttss.pl/proxy_tram.php/services/passageInfo/stopPassages/stop` (trams)

### Vehicle Tracking APIs
- **Bus Vehicles**: `https://ttss.pl/proxy_bus.php/geoserviceDispatcher/services/vehicleinfo/vehicles?positionType=CORRECTED&colorType=ROUTE_BASED&lastUpdate=0`
- **Tram Vehicles**: `https://ttss.pl/proxy_tram.php/geoserviceDispatcher/services/vehicleinfo/vehicles?positionType=CORRECTED&colorType=ROUTE_BASED&lastUpdate=0`

### Architecture Benefits
- **No Screen Scraping**: Direct API integration for reliability
- **Real-time Data**: Live departure times and vehicle positions from official sources
- **Multiple Platforms**: Automatically aggregates data from all platforms of the same stop
- **Live Vehicle Tracking**: Real-time GPS positions with heading and route information
- **Fault Tolerance**: Continues working even if individual API calls fail


