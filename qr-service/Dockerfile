FROM openjdk:17

ADD target/qr-service.jar qr-service.jar

EXPOSE 8082
EXPOSE 5434
EXPOSE 5432
EXPOSE 29092

ENTRYPOINT ["java", "-jar", "qr-service.jar"]