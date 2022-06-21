FROM openjdk:17

ADD target/medical.jar medical.jar

EXPOSE 8081
EXPOSE 5433
EXPOSE 5432
EXPOSE 29092

ENTRYPOINT ["java", "-jar", "medical.jar"]