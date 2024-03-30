FROM openjdk:11.0.16
# EXPOSE 8081:8080
RUN mkdir /app
COPY hanoi-backend/build/libs/*-all.jar /app/server.jar
ENTRYPOINT ["java","-jar","/app/server.jar"]