#!/bin/bash

set -e

# https://stackoverflow.com/a/4774063
BASE_PATH="$( cd "$(dirname "$0")/.." ; pwd -P )"

VERSION="$(cat VERSION.txt | tr -d '[:space:]')"
DOCS_PATH="${BASE_PATH}/target/site/apidocs"
TARBALL="${VERSION}.tar.gz"
VERSIONED_DEST="gs://ua-web-ci-library-docs/libraries/java/$TARBALL"
LATEST_DEST="gs://ua-web-ci-library-docs/libraries/java/latest.tar.gz"

if [ ! -d "${DOCS_PATH}" ]; then
  echo "Error: ${DOCS_PATH} does not exist or is not a directory"
  false
fi

cd $DOCS_PATH
tar -czf $TARBALL *

set -x

# FIXME:

# if ["${BRANCH_NAME}" = "master"]; then
  gsutil cp $TARBALL gs://ua-web-ci-library-docs/libraries/java/$TARBALL
  gsutil cp $VERSIONED_DEST $LATEST_DEST
# else
#   echo "Not uploading tarball on branch ${BRANCH_NAME}"
# fi
