#!/usr/bin/env bash
set -euo pipefail

target="${1}"
branch="${2:-unstable}"

fly -t "${target}" set-pipeline  \
    --var git.uri="https://github.com/encodeering/docker-ci.git" \
    --var git.branch="${branch}" \
    --pipeline="docker-ci"       \
    --config concourse.yml

fly -t "${target}" check-resource -r "docker-ci/code"
fly -t "${target}" trigger-job -j "docker-ci/build" --watch
