set -e

echo "Running Gatling Test"

docker-compose -f nft/local-docker/docker-compose.yml up -d

echo "Waiting for a bit so you have time to look at the logs of the NFT container"

sleep 300

echo "Stopping and removing docker instances including NFT"

docker-compose -f nft/local-docker/docker-compose.yml down
