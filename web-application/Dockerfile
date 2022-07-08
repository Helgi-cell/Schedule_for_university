#DOCKER BUILD :    docker build -t web-application:0.0.1 .
# DOCKER RUN  :       docker run --net oleg_university --ip 192.168.5.11 -d -p 8899:8899 --name webapp <container ID>

FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/web-application-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} webapp.jar
#EXPOSE 8899
ENTRYPOINT ["java","-jar","webapp.jar"]