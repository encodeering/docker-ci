#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>
# configuration
#   $1 module directory, can be "."
#   $2 script

set -e

export ROOT="$PWD"

traverse () {
    local subdirectory="$1"; shift
    local extension="$1"; shift
    local target="$1"; shift
    local function="$1"; shift

    local base=`pwd -P`
    local parent="."

    while
        local file="${base}/${0%/*}/${parent}/${subdirectory}/${target//.//}.${extension}"
              file=`readlink -m "${file}"`

        [[ ${file} == ${ROOT}* ]] || break

        [ -f "${file}" ] && "${function}" "${file}" "$@"

        parent="../${parent}"
    do
        :
    done
}

import () {
    local target="$1"; shift

    set -o allexport

    traverse "lib/source" "source" "${target}" source
    traverse "."          "source" "${target}" source

    set +o allexport
}

script () {
    local target="$1"; shift

    set -o allexport

    traverse "lib/script" "sh" "${target}" bash "$@"
    traverse "."          "sh" "${target}" bash "$@"

    set +o allexport
}

typeset -fx traverse import script

MODULE="$1"; shift 1;
SCRIPT="$1"; shift 1;

(cd "${MODULE}"; bash "${SCRIPT}" "$@")
