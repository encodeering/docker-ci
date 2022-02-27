#!/usr/bin/env bash

set -e

find registry -name image.tar -type f -exec docker load -i {} \;
