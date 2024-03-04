FROM eclipse-temurin:19-jdk-alpine
WORKDIR /app
COPY target/spring-boot-docker.jar spring-boot-docker.jar
EXPOSE 8080
CMD ["java","-jar","/spring-boot-docker.jar"]