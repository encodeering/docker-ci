#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

set -e

import com.encodeering.docker.lang
import com.encodeering.docker.config

                          { script "com.encodeering.docker.binfmt" -a "${ARCH}"; }
[ -z "${SCM_SOURCE}" ] || { script "com.encodeering.docker.scm" -s "${SCM_SOURCE}" -d "${SCM_TARGET}" -c "${SCM_COMMIT}"; }
