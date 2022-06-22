#!/usr/bin/env bash

MY_MVN="./mvnw"

function dockerUp() {
    $MY_MVN docker-compose:up
}

function dockerDown() {
    $MY_MVN docker-compose:down
}

function siteAll() {
    $MY_MVN site site:deploy
}

function secondSetup() {
		$MY_MVN dependency:purge-local-repository clean dependency:resolve dependency:resolve-plugins dependency:tree dependency:sources dependency:analyze-dep-mgt dependency:analyze-report install site site:deploy liberty:dev
}

function firstSetup() {
		./mvnw
}

function devTest() {
		./mvnw clean package -DskipTests=true
    ./mvnw liberty:create liberty:install-feature
    ./mvnw liberty:configure-arquillian
    ./mvnw failsafe:integration-test
}

function main() {
	#firstSetup
	#secondSetup
	devTest
}

main
