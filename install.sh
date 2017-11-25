#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

VERSION=0.1.1

wget -q -O - "https://github.com/encodeering/docker-ci/releases/download/${VERSION}/ci-${VERSION}.tar" | tar xvf - --strip-components=1