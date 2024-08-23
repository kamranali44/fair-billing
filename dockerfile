FROM openjdk:11.0.11-jdk-slim

LABEL author="Kamran Ali"

RUN apt-get update && apt-get install -y bash

WORKDIR /usr/src/app

COPY target/FairBilling-0.0.1-SNAPSHOT-jar-with-dependencies.jar fairBilling.jar

# copy log files into container
COPY src/main/resources/*.txt /logs/

ENTRYPOINT ["java", "-jar", "/usr/src/app/fairBilling.jar"]