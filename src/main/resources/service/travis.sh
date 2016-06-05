#!/usr/bin/env bash
set +x
set -v

docker run --rm encodeering/travis-$arch:1.8.2 "
    set -e
    travis login --github-token $token

    travis show -r $account/$project $branch | head -n1 | sed -r 's!.*#([0-9]+).*!\1!g' | xargs travis restart -r $account/$project $1

            value='notyet';                \
    while grep -q 'notyet' <<< \$value; do \
        sleep 60;                          \
               value=\`(travis show -r $account/$project $branch 2>&1 || echo Finished: not yet) | awk '/Finished.+(not yet)/ { print \$2\$3 }'\`; \
        echo \$value:\`date\`;             \
    done

    travis show -r $account/$project $branch
    travis show -r $account/$project $branch | awk '/State.+(passed)/ { print \$2 }' | grep -q 'passed'
"
