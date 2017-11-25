#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>
# configuration
#   env.DOCKER_USERNAME
#   env.DOCKER_PASSWORD

set -e

import com.encodeering.docker.docker

retry 2 docker login -u "${DOCKER_USERNAME}" -p "${DOCKER_PASSWORD}"
retry 2 docker push     "${DOCKER_IMAGE}"

for TAG in "$@"
do
    [ -z "$TAG" ] || retry 2 docker push "${DOCKER_IMAGE}-${TAG}"
done
