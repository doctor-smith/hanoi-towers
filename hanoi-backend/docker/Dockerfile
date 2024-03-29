FROM openjdk:8-jre-alpine

RUN apk upgrade && apk update

ENV APPLICATION_USER ktor

RUN adduser -D -g '' $APPLICATION_USER

RUN mkdir /app
RUN chown -R $APPLICATION_USER /app

USER $APPLICATION_USER

COPY hanoi-backend/build/libs/hanoi-backend-all.jar /app/server.jar
# COPY ./hanoi-backend/web/css/style.css /app/css/style.css

WORKDIR /app

CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "server.jar"]

