#!/bin/bash

# This script is used to compile, test, generate documentation and package the application.

## Variables
SRC_MAIN=src/main/java
SRC_TEST=src/test/java
CLASSES_DEST=target/classes
CLASSES_DEST_TEST=target/test_classes
DIST=dist
LIB=lib
DOC=docs
MANIFEST=MANIFEST.MF

CLASSPATH="$CLASSES_DEST;$LIB/jackson-core-2.14.0.jar;$LIB/jackson-databind-2.14.0.jar;$LIB/jackson-annotations-2.14.0.jar"
PACKAGE_RACINE=com/company/inventory

## Commands
compile() {
    javac -cp "$CLASSPATH" -sourcepath "$SRC_MAIN" -d "$CLASSES_DEST" "$SRC_MAIN/$PACKAGE_RACINE/Main.java"
}

compile-test() {
    compile
    javac -cp "$CLASSES_DEST_TEST;$CLASSPATH;$LIB/junit-4.13.2.jar" -sourcepath "$SRC_TEST" -d "$CLASSES_DEST_TEST" "$SRC_TEST/$PACKAGE_RACINE/VehicleDAOTest.java"
}

test(){
    java -cp "$CLASSES_DEST_TEST;$CLASSPATH;$LIB/junit-4.13.2.jar;$LIB/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore "${PACKAGE_RACINE//\//.}.VehicleDAOTest"
}

docs() {
    javadoc -cp "$CLASSPATH" -sourcepath "$SRC_MAIN" -d "$DOC" -subpackages "${PACKAGE_RACINE//\//.}"
}

getjar() {
    jar cvfm $DIST/vehicleInventory.jar $MANIFEST -C $CLASSES_DEST . -C $LIB jackson-annotations-2.14.0.jar -C $LIB jackson-core-2.14.0.jar -C $LIB jackson-databind-2.14.0.jar
}

run() {
    java -classpath "$CLASSPATH" "${PACKAGE_RACINE//\//.}.Main"
    # or java -jar "$DIST"/vehicleInventory.jar
}

if [ $# -eq 0 ]; then
    echo "No arguments provided. Performing default actions: compile, compile-test, docs, getjar."
    compile
    compile-test
    getjar
    exit 0
fi

if [ "$1" == "compile" ]; then
    compile

elif [ "$1" == "compile-test" ]; then
    compile-test

elif [ "$1" == "test" ]; then
    test

elif [ "$1" == "docs" ]; then
    docs

elif [ "$1" == "getjar" ]; then
    getjar

elif [ "$1" == "run" ]; then
    run

elif [ "$1" == "clean" ]; then
    rm -rf "$CLASSES_DEST"/*
    rm -rf "$CLASSES_DEST_TEST"/*
    rm -rf "$DOC"/*
    rm -rf "$DIST"/vehicleInventory.jar

else
    echo "Invalid argument: $1"
    exit 1
fi