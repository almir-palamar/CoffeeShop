FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
EXPOSE 8080
WORKDIR .
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip
ENTRYPOINT ["java","-jar", "target/CoffeeShop-0.0.1-SNAPSHOT.jar"]