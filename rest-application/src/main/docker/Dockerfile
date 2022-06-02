FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
ADD rest-application-0.0.1-SNAPSHOT.jar restapp.jar
RUN bash -c 'touch /restapp.jar'
EXPOSE 8090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/restapp.jar"]