cd ..
./gradlew hanoi-backend:clean && \
./gradlew hanoi-backend:build && \
./gradlew hanoi-backend:buildFatJar && \
docker-compose down --remove-orphans && \
docker-compose up -d --build hanoi_backend