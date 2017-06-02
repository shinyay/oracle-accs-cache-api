#!/bin/sh

#############################################################
# Maven Grizzly Project Generator
version="1.0.2"
#
# HISTORY:
#
# * 17/06/02 - v1.0.2  - Artifact Version Down to b03
# * 17/06/02 - v1.0.1  - Artifact Version Up to b04 from b03
# * 17/04/09 - v1.0.0  - First Creation
#
#############################################################

# https://maven-repository.com/artifact/org.glassfish.jersey.archetypes/jersey-quickstart-grizzly2
JERSEY_QUICKSTART_GRIZZLY2_VERSION=2.26-b03

GROUP_ID="$1"
ARTIFACT_ID="$2"

function mainScript() {
mvn archetype:generate \
  -DarchetypeGroupId=org.glassfish.jersey.archetypes \
  -DarchetypeArtifactId=jersey-quickstart-grizzly2 \
  -DarchetypeVersion=${JERSEY_QUICKSTART_GRIZZLY2_VERSION} \
  -DinteractiveMode=false \
  -DgroupId=${GROUP_ID} \
  -DartifactId=${ARTIFACT_ID} \
  -Dversion=1.0.0-SNAPSHOT \
  -Dpackage=${GROUP_ID}
}

function usage() {
  cat <<EOF 1>&2
$(basename ${0}) is a tool for ...

Usage:
  $(basename ${0}) [GROUP_ID] [ARTIFACT_ID]

Options:
  --version, -v     print $(basename ${0}) ${version}
  --help, -h        print help
EOF
}

# Check Arguments
if [ $# -ne 2 ]; then
  usage
  exit 1
fi

if [ ! -d ./maven ]; then
  mkdir maven
fi

cd maven
mainScript
