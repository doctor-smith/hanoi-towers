cd ..
./gradlew hanoi-frontend:clean
./gradlew hanoi-frontend:build
docker-compose down --remove-orphans
docker-compose up -d --build hanoi_frontend