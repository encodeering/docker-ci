#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

set -e

import com.encodeering.docker.docker

pushif () {
    if          docker inspect "$1" > /dev/null 2>&1; then
        retry 2 docker push    "$1"
    fi
}

retry 2 docker login -u "${DOCKER_USERNAME}" -p "${DOCKER_PASSWORD}"

pushif "${DOCKER_IMAGE}"

for TAG in "$@"
do
    [ -z "$TAG" ] || pushif "${DOCKER_IMAGE}-${TAG}"
done
