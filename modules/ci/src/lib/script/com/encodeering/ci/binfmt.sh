#!/usr/bin/env bash

set -e

emulate () {
    mount binfmt_misc -t binfmt_misc /proc/sys/fs/binfmt_misc

    find /proc/sys/fs/binfmt_misc -type f -name 'qemu-*' -exec sh -c 'echo -1 > {}' \;

    binfmt-qemu.sh --qemu-suffix '-static' --qemu-path /usr/bin -p yes
}

emulate
