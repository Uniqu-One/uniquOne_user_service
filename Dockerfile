FROM adoptopenjdk/openjdk11
COPY build/libs/msa_user_service-0.0.1-SNAPSHOT.jar app/user-service.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "user-service.jar"]