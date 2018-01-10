#!/usr/bin/env bash
# author: Michael Clausen <encodeering@gmail.com>

set -e

import com.encodeering.ci.lang
import com.encodeering.ci.config

                          { script "com.encodeering.ci.binfmt" -a "${ARCH}"; }
[ -z "${SCM_SOURCE}" ] || { script "com.encodeering.ci.scm" -s "${SCM_SOURCE}" -d "${SCM_TARGET}" -c "${SCM_COMMIT}"; }
