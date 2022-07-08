FROM maven:3.6.2-jdk-11
WORKDIR /app

COPY pom.xml ./
COPY src/ ./src/
RUN mvn package


EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/target/kafka-rest-0.0.1-SNAPSHOT.jar"]

#CMD ["java", "-jar", "/app/target/hello-world-1.0.0.jar"]