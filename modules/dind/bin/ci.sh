#!/usr/bin/env bash
set -euo pipefail

unpack () {
    local archive="${1}"
    local target="${2}"

    local   package="/usr/local/lib/ci/${archive}.tar"
    [ -f "${package}" ] || exit 1

    cat < "${package}" | tar xvf - -C "${target}" --strip-components=1
}

unpack "${1}" "${2:-.}"
