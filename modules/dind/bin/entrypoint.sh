#!/usr/bin/env bash
set -e

source docker-dind

start_docker

exec "$@"
