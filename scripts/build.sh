#!/usr/bin/env bash
#/bin/bash

cd ..
GRADLE="./gradlew"
$GRADLE assembleRelease
$GRADLE crashlyticsUploadDistributionRelease
RELEASEFILE="${CIRCLE_ARTIFACTS}/incubasystest-release-${CIRCLE_BUILD_NUM}-${CIRCLE_SHA1}.apk"
cp ./app/build/outputs/apk/app-release.apk ${RELEASEFILE}