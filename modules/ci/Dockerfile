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
 &&        mv /docker/* /bin/          \
 &&        chmod +x     /bin/docker*   \
 &&        curl -sSL "https://raw.githubusercontent.com/meAmidos/dcind/6efeeaa/docker-lib.sh"          --output /usr/local/bin/docker-dind    \
 &&        curl -sSL "https://raw.githubusercontent.com/qemu/qemu/719fab3/scripts/qemu-binfmt-conf.sh" --output /usr/local/bin/binfmt-qemu.sh

COPY       bin/*.sh  /usr/local/bin/
COPY       ci-*.tar  /usr/local/lib/ci/docker.tar

RUN        chmod +x  /usr/local/bin/*.sh

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
