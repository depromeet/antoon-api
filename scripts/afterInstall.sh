CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running antoon-core-api is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq 8080 ]; then
  TARGET_PORT=8081
elif [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8080
else
  echo "> No antoon-core-api is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
    echo "> Kill antoon-core-api running at ${TARGET_PORT}."
    sudo kill ${TARGET_PID}
fi

docker pull antoonapi/antoon-core-api:staging

docker run -d --publish ${TARGET_PORT}:${TARGET_PORT} \
    -v /etc/localtime:/etc/localtime:ro \
    -v /usr/share/zoneinfo/Asia/Seoul:/etc/timezone:ro \
    --restart unless-stopped \
    --memory 800m --memory-swap 2g \
    -it --detach \
    --name antoon-core-api antoonapi/antoon-core-api:staging /bin/bash

echo ">Now new antoon-core-api runs at ${TARGET_PORT}"