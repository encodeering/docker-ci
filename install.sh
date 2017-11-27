#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

latest () {
    echo `curl --silent https://api.github.com/repos/encodeering/docker-ci/releases/latest | awk '/tag_name/ { print $2 }' | cut -d '"' -f 2`
}

VERSION=${1:-`latest`}

wget -q -O - "https://github.com/encodeering/docker-ci/releases/download/${VERSION}/ci-${VERSION}.tar" | tar xvf - --strip-components=1