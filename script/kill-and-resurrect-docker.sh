cd ..
docker-compose down --remove-orphans
docker builder prune -af
docker system prune -af
docker volume prune -af
docker image prune -af
docker-compose up -d --build



