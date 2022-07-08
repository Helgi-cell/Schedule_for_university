# create static network :    docker network create --subnet=192.168.5.7/16 oleg_university

#DOCKER BUILD :    docker build -t rest-application:0.0.1 .
# DOCKER RUN  :     docker run --net oleg_university --ip 192.168.5.10 -d -p 8898:8898 --name restapp <container ID>

FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/rest-application-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} restapp.jar
ARG DB_FILE=university.mv.db
COPY ${DB_FILE} university.mv.db
#EXPOSE 8898
ENTRYPOINT ["java","-jar","restapp.jar"]
