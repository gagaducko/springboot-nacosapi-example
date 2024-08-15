FROM openjdk:17

LABEL authors="gagaduck"

COPY ./target/nacos-discovery-service-0.0.1-SNAPSHOT.jar /app.jar

COPY ./skywalking-agent /usr/local/server/skywalking

ENTRYPOINT ["java", "-javaagent:/usr/local/server/skywalking/skywalking-agent.jar", "-Dskywalking.agent.service_name=nacos-discovery-service", "-Dskywalking.collector.backend_service=server:11800", "-jar", "/app.jar"]