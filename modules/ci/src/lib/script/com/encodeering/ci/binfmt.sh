#!/usr/bin/env bash

set -e

while getopts "a:" opt; do
	case "${opt}" in
		a)
			architecture="${OPTARG}"
			;;
		*)
            echo "usage: $0 -a [armhf,..]"
            exit 1
			;;
	esac
done

emulate () {
    local architecture="$1";

    [    "${architecture}" == `dpkg --print-architecture` ] && exit 0

    case "${architecture}" in
        armhf )
            local ARM=':arm:M::\x7fELF\x01\x01\x01\x00\x00\x00\x00\x00\x00\x00\x00\x00\x02\x00\x28\x00:\xff\xff\xff\xff\xff\xff\xff\x00\xff\xff\xff\xff\xff\xff\xff\xff\xfe\xff\xff\xff:/usr/bin/qemu-arm-static:'

            echo -1       > /proc/sys/fs/binfmt_misc/arm || true
            echo "${ARM}" > /proc/sys/fs/binfmt_misc/register
            cat             /proc/sys/fs/binfmt_misc/arm
            ;;
    esac
}

emulate "${architecture}"
