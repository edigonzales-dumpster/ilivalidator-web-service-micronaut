#!/bin/sh

githash=$1
if [ "x$githash" = "x" ]; then
    githash='localbuild'
fi

buildident=$2
if [ "x$buildident" = "x" ]; then
    buildident='localbuild'
fi

build_timestamp=$(date '+%Y-%m-%d_%H:%M:%S')

docker build . -t sogis/ilivalidator-web-service-micronaut:$buildident \
    --label gretl.created=$build_timestamp --label gretl.git_commit=$githash --label gretl.travis_build=$buildident

docker tag sogis/ilivalidator-web-service-micronaut:$buildident sogis/ilivalidator-web-service-micronaut:latest    
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 8080:8080 sogis/ilivalidator-web-service-micronaut"
