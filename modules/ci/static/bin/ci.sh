#!/usr/bin/env bash
# https://stackoverflow.com/questions/37540717/flatten-nested-json-using-jq
# https://stackoverflow.com/questions/48512914/exporting-json-to-environment-variables
set -euo pipefail

unpack () {
    local archive="${1}"
    local target="${2}"

    local   package="/usr/local/lib/ci/${archive}.tar"
    [ -f "${package}" ] || exit 1

    cat < "${package}" | tar xvf - -C "${target}" --strip-components=1
}

environmenty () {
    cat -                                                                                                      \
        | jq '. as $in | reduce leaf_paths as $path ({}; . + { ($path | join ("_")): $in | getpath ($path) })' \
        | jq -r 'keys[] as $k | "\($k | ascii_upcase)=\(.[$k])"'                                               \
        | xargs
}

case "${1}" in
  "unpack") unpack "${2}" "${3:-.}";;
     "env") environmenty;;
esac
