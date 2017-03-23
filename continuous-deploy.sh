CURR_TAG=${COMMIT_BUILD_TYPE}${COMMIT_BUILD_NUMBER}

echo "BEFORE:"
docker images

for name in discovery-service fund-service trade-service invest-service inventory-service web-gateway sequence-service
do
    echo "docker tag docker-registry:5000/${name}:${CURR_TAG} docker-registry:5000/${name}:latest"
    docker tag docker-registry:5000/${name}:${CURR_TAG} docker-registry:5000/${name}:latest
done

echo "AFTER:"
docker images

echo "cd ${COMMIT_WORKSPACE}"

cd ${COMMIT_WORKSPACE}
docker-compose -f infrastructure-cd.yml up -d
sleep 10

HOST_IP=$(ip route|awk '/default/ { print $3 }')
./createdb.sh ${HOST_IP}
./migratedb.sh ${HOST_IP}
docker-compose -f application-cd.yml up -d
