FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD users-microservice-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8100
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Dspring.data.mongodb.uri=mongodb://mongodb/hazardmanager -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
