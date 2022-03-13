# https://github.com/fhivemind/concourse-dind
# https://github.com/meAmidos/dcind
FROM       debian:bullseye-slim

ARG        DEBIAN_FRONTEND=noninteractive

RUN        apt-get    update       \
 &&        apt-get -y install      \
               bash                \
               binfmt-support      \
               ca-certificates     \
               curl                \
               git                 \
               jq                  \
               iptables            \
               iproute2            \
               net-tools           \
               qemu-user-static    \
               util-linux          \
               wget                \
               xz-utils            \
 &&        rm -rf /var/cache/apk/* \
 &&        rm -rf /root/.cache

ARG        DOCKER_CHANNEL=stable
ARG        DOCKER_VERSION=20.10.12
ARG        DOCKER_BUILDX_VERSION=0.7.0

RUN        curl -sSL "https://download.docker.com/linux/static/${DOCKER_CHANNEL}/x86_64/docker-${DOCKER_VERSION}.tgz" | tar zx \
 &&        mv /docker/* /bin/         \
 &&        chmod +x     /bin/docker*

ARG        CI_VERSION=0.1.15

RUN        mkdir -p /usr/local/lib/ci/ \
 &&        curl -sSL "https://raw.githubusercontent.com/meAmidos/dcind/6efeeaa/docker-lib.sh"          --output /usr/local/bin/docker-dind    \
 &&        curl -sSL "https://raw.githubusercontent.com/qemu/qemu/719fab3/scripts/qemu-binfmt-conf.sh" --output /usr/local/bin/binfmt-qemu.sh \
 &&        curl -sSL "https://github.com/encodeering/docker-ci/releases/download/${CI_VERSION}/ci-${CI_VERSION}.tar" --output /usr/local/lib/ci/docker.tar

COPY       bin/*.sh   /usr/local/bin/
RUN        chmod +x   /usr/local/bin/*.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
