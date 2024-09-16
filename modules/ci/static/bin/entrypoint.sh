#!/usr/bin/env bash
set -e

echo "Mirror: ${DOCKER_MIRROR}"

source docker-dind "" "${DOCKER_MIRROR}"

start_docker

exec "$@"
