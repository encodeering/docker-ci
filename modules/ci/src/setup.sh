#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

set -e

import com.encodeering.docker.lang
import com.encodeering.docker.config

                  { script "com.encodeering.docker.binfmt" -a "${ARCH}"; }
[ "$#" -ge 2 ] && { script "com.encodeering.docker.scm" -s "$1" -d "$2" -c "$3"; } || { true; }
