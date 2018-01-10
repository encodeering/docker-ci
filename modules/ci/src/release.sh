#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

set -e

import com.encodeering.ci.docker

docker-login
docker-push

for TAG in "$@"
do
    [ -z "$TAG" ] || docker-push --suffix "${TAG}"
done
