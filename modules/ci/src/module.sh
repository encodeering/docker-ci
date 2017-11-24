#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>
# configuration
#   $1 module directory, can be "."
#   $2 script

set -e

export ROOT="$PWD"

traverse () {
    SUBDIRECTORY="$1"; shift
    EXTENSION="$1"; shift
    TARGET="$1"; shift
    FUNCTION="$1"; shift

    BASE=`pwd -P`
    PARENT="."

    while
        FILE="${BASE}/${0%/*}/${PARENT}/${SUBDIRECTORY}/${TARGET//.//}.${EXTENSION}"
        FILE=`readlink -m "${FILE}"`

        [[ ${FILE} == ${ROOT}* ]] || break

        [ -f "${FILE}" ] && "${FUNCTION}" "${FILE}" "$@"

        PARENT="../${PARENT}"
    do
        :
    done
}

import () {
    set -o allexport

    traverse "lib/source" "source" "$1" source
    traverse "."          "source" "$1" source

    set +o allexport
}

script () {
    set -o allexport

    traverse "lib/script" "sh" "$1" bash "$@"
    traverse "."          "sh" "$1" bash "$@"

    set +o allexport
}

typeset -fx traverse import script

MODULE="$1"; shift 1;
SCRIPT="$1"; shift 1;

(cd "${MODULE}"; bash "${SCRIPT}" "$@")
