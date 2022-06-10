if [ -d /home/ubuntu/build ]; then
    rm -rf /home/ubuntu/build
fi

mkdir -vp /home/ubuntu/build

docker stop antoon-core-api
docker rm antoon-core-api

if [[ "$(docker images -q antoonapi/antoon-core-api:prod 2> /dev/null)" != "" ]]; then
docker rmi -f $(docker images --format '{{.Repository}}:{{.Tag}}' --filter=reference='antoonapi/antoon-core-api:prod')
fi