FROM openjdk:8
EXPOSE 80
ADD target/rewards-service.jar rewards-service.jar
ENTRYPOINT ["java", "-jar", "/rewards-service.jar"]