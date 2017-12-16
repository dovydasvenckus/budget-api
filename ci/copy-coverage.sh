#!/bin/sh

cd $SHIPPABLE_BUILD_DIR
mkdir -p shippable/codecoverage/target/site/jacoco
cp build/reports/jacoco/test/jacocoTestReport.xml shippable/codecoverage/target/site/jacoco/jacoco.xml
cp -r build/reports/jacoco/test/html shippable/codecoverage/target/site/jacoco/