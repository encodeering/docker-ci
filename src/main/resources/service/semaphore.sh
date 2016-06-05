#!/usr/bin/env bash
set +x
set -v

docker run --rm encodeering/semaphore-$arch:0.1.0 "
    set -e
    export SEMAPHORECI_API_KEY=$token

    semaphore job rebuild --branch $branch $project

            value='pending';                \
    while grep -q 'pending' <<< \$value; do \
        sleep 60;                           \
               value=\`(semaphore project status --branch $branch --attributes result $project 2>&1 || echo result: pending) | awk '/result.+(pending)/ { print \$2 }'\`; \
        echo \$value:\`date\`;              \
    done

    semaphore project status --branch $branch                     $project
    semaphore project status --branch $branch --attributes result $project | awk '/result.+(passed)/ { print \$2 }' | grep -q 'passed'
"
