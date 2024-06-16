FROM openjdk:17-jdk-slim
# EXPOSE 8081:8080
RUN mkdir /app
WORKDIR /app
COPY hanoi-backend/build/libs/*-all.jar /app/server.jar
ENTRYPOINT ["java","-jar","/app/server.jar"]