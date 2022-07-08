FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/rest-application-0.0.1-SNAPSHOT.jar
#WORKDIR /opt/app
COPY ${JAR_FILE} restapp.jar
#VOLUME /tmp
#ADD rest-application-0.0.1-SNAPSHOT.jar restapp.jar
#RUN bash -c 'touch /restapp.jar'
EXPOSE 8899
ENTRYPOINT ["java","-jar","restapp.jar"]
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/restapp.jar"]