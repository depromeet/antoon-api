if [ -d /home/ubuntu/build ]; then
    rm -rf /home/ubuntu/build
fi
# 만약 /home/ubuntu/build 디렉토리가 존재하면 지운다는 의미입니다.

mkdir -vp /home/ubuntu/build
# 다시 새로운 /home/ubuntu/build 디렉토리를 생성합니다.

docker stop antoon-core-api
docker rm antoon-core-api
# 저는 Docker를 이용할 것이기 때문에, 돌가가고 있는 Docker Container를 중지시키고, 제거합니다.
# 이후 afterinstall.bash 파일에서 새롭게 받아온 파일을 사용하여 다시 Docker Container를 띄울 예정입니다.
# [Your Docker Container Name] 예시) woomin-facebook-codedeploy

if [[ "$(docker images -q antoonapi/antoon-core-api:latest 2> /dev/null)" != "" ]]; then
docker rmi -f $(docker images --format '{{.Repository}}:{{.Tag}}' --filter=reference='antoonapi/antoon-core-api:latest')
fi
# 해당 Docker Image가 존재하면 image를 제거한다는 뜻입니다.
# 이후 afterinstall.bash 파일에서 갱신된 이미지를 불러올 것입니다.
# [Your DockerHub ID]/[Your Repository Name]:[Your version] 예시) dal96k/woomin-facebook:latest