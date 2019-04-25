FROM openjdk:8-jre-alpine
MAINTAINER http://davidson.com

# environment variables
ENV FAST_SERVER_HOME=/opt/fast-server-home \
    STACK=fast-server \
    JAVA_OPTS='' \
    SPRING_PROFILES_ACTIVE=dev

# exposed ports
EXPOSE 8080

# copy jar into the container
COPY target/fast-server-*.jar $FAST_SERVER_HOME/fast-server.jar

# command run
ENTRYPOINT java -Dstack.name=$STACK \
            -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
            -DRESET_OFFSET=${RESET_OFFSET} \
            -XX:+UseG1GC \
            -XX:+UseStringDeduplication \
            $JAVA_OPTS \
            -jar $FAST_SERVER_HOME/fast-server.jar
