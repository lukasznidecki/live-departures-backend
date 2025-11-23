FROM eclipse-temurin:25-jdk AS builder

ENV LANGUAGE='en_US:en'

WORKDIR /build
COPY pom.xml .
COPY src src/
COPY .mvn .mvn/
COPY mvnw .

RUN ./mvnw package -DskipTests -Dquarkus.package.type=uber-jar

FROM eclipse-temurin:25-jre

ENV LANGUAGE='en_US:en'

ARG APP_VERSION=dev
LABEL app.version=${APP_VERSION}
ENV APP_VERSION=${APP_VERSION}

WORKDIR /deployments

COPY --chown=1000 target/quarkus-app/lib/ /deployments/lib/
COPY --chown=1000 target/quarkus-app/*.jar /deployments/
COPY --chown=1000 target/quarkus-app/app/ /deployments/app/
COPY --chown=1000 target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 1000
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

ENTRYPOINT ["java", "-jar", "/deployments/quarkus-run.jar"]