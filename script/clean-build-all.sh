cd ..
./gradlew clean
./gradlew hanoi-frontend:clean
./gradlew hanoi-backend:clean

./gradlew build
./gradlew hanoi-frontend:build
./gradlew hanoi-backend:build
./gradlew hanoi-backend:buildFatJar