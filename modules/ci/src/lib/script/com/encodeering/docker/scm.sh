#!/usr/bin/env bash

set -e

commit=master

while getopts "s:c:d:" opt; do
	case "${opt}" in
		s)
			source="${OPTARG}"
			;;
		c)
			commit="${OPTARG}"
			;;
        d)
			directory="${OPTARG}"
			;;
		*)
            echo "usage: $0 -s https://github.com/.. -c xxx-sha-xxx -d directory"
            exit 1
			;;
	esac
done

checkout () {
    local source="$1";
    local commit="$2";
    local directory="$3";

    mkdir -p "${directory}"
    cd       "${directory}"

    git clone "${source}" .
    git checkout "${commit}"
}

checkout "${source}" "${commit}" "${directory}"
