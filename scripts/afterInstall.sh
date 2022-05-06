docker pull antoonapi/antoon-core-api:latest
docker run -d --publish 8080:8080 \
    -v /etc/localtime:/etc/localtime:ro \
    -v /usr/share/zoneinfo/Asia/Seoul:/etc/timezone:ro \
    --restart unless-stopped \
    --memory 800m --memory-swap 2g \
    -it --detach \
    --name antoon-core-api antoonapi/antoon-core-api:latest /bin/bash