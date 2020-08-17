#
# Build stage
#
FROM maven:3.6.3-jdk-14 AS build
ARG JAR_FILE
WORKDIR /app
ADD . /app
RUN mvn -v
RUN rm /app${JAR_FILE}
RUN mvn clean install -DskipTests
#RUN ls -ld $PWD/*
#RUN mvn verify clean --fail-never package

#
# Package stage
#
FROM openjdk:14.0.2-jdk
ARG JAR_FILE
COPY --from=build /app${JAR_FILE} /usr/local/lib/ymapi.jar
EXPOSE 8080
RUN jar tf /usr/local/lib/ymapi.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/ymapi.jar"]