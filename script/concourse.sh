#!/usr/bin/env bash
# bash <(curl -fsSL https://raw.githubusercontent.com/encodeering/docker-ci/master/script/concourse.sh) pipeline developer encodeering docker-base https://github.com/encodeering/docker-base.git unstable?
set -euo pipefail

pipeline () {
    local target="${1}"
    local repository="${2}"
    local name="${3}"
    local location="${4}"
    local branch="${5}"

    local                   version="unstable"
    [ -f "matrix.json" ] && version=$(jq -r '.concourse .dind' < matrix.json)

    curl -sSL "https://raw.githubusercontent.com/encodeering/docker-ci/${version}/modules/pipeline/docker-self.yml" \
        | fly -t "${target}" set-pipeline --pipeline     "${name}-self"                                             \
                                          --var pipeline="${name}"                                                  \
                                          --var repository="${repository}"                                          \
                                          --var git.uri="${location}"                                               \
                                          --var git.branch="${branch}"                                              \
                                          --config -
}

case "${1}" in
  pipeline) pipeline "${2}" "${3}" "${4}" "${5}" "${6:-}";;
         *) false;;
esac || exit 1
