#!/bin/bash

set -e

# https://stackoverflow.com/a/4774063
BASE_PATH="$( cd "$(dirname "$0")/.." ; pwd -P )"

VERSION="$(cat VERSION.txt | tr -d '[:space:]')"
DOCS_PATH="${BASE_PATH}/target/site/apidocs"
TARBALL="${VERSION}.tar.gz"

if [ ! -d "${DOCS_PATH}" ]; then
  echo "Error: ${DOCS_PATH} does not exist or is not a directory"
  false
fi

cd $DOCS_PATH
tar -czf $TARBALL *

set -x

# TODO: if master

gsutil cp $TARBALL gs://ua-web-ci-library-docs/libraries/java/$TARBALL
