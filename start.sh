# Pare e remova containers antigos
docker-compose down
docker system prune -a

# Reconstrua
docker-compose build --no-cache

# Suba os serviços
docker-compose up