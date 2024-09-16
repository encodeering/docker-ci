#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

set -e

import com.encodeering.ci.docker

docker-scan "${REPOSITORY}"

docker-login
docker-push
