#!/bin/bash

base_url="http://localhost:8080"

if [ ! -z "$1" ]
  then
    base_url="$1"
fi

if [ ! -z "$2" ]
  then
    export BROWSERSTACK_URL="$2"
fi

export BABBAGE_URL=$base_url

echo $BABBAGE_URL

echo Running tests against: ${base_url}

mvn clean test

exit $? # return the code from the last command.
