FROM openjdk:17
COPY ./build/libs/NewsJam-0.0.1-SNAPSHOT.jar NewsJam.jar
ENTRYPOINT ["java", "-jar", "NewsJam.jar"]
