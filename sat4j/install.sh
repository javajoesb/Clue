#!/bin/bash
type -P mvn &>/dev/null || { echo "I can't find mvn, please install/add to PATH.  Aborting." >&2; exit 1; }
echo "Using $(type -P mvn)"
mvn install:install-file -DgroupId=org.sat4j -DartifactId=org.sat4j.core -Dversion=2.2.3 -Dpackaging=jar -Dfile=org.sat4j.core.jar -Dsources=org.sat4j.core-src.jar
