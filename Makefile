SRC_MAIN=src/main/java
SRC_TEST=src/test/java
CLASSES_DEST=target/classes
CLASSES_DEST_TEST=target/test_classes
DIST=dist
LIB=lib
DOC=docs
MANIFEST=MANIFEST.MF

CLASSPATH=$(CLASSES_DEST):$(LIB)/junit-4.13.2.jar:$(LIB)/hamcrest-core-1.3.jar
PACKAGE_RACINE=com/company/inventory

all: compile compile-test docs getjar

compile:
	javac -cp $(CLASSES_DEST) -sourcepath $(SRC_MAIN) -d $(CLASSES_DEST) $(SRC_MAIN)/$(PACKAGE_RACINE)/Main.java

compile-test:
	javac -cp $(CLASSES_DEST):$(CLASSES_DEST_TEST):$(LIB)/junit-4.13.2.jar -sourcepath $(SRC_TEST) -d $(CLASSES_DEST_TEST) $(SRC_TEST)/$(PACKAGE_RACINE)/VehicleDAOTest.java

docs:
	javadoc -cp $(CLASSES_DEST) -sourcepath $(SRC_MAIN) -d $(DOC) -subpackages $(subst /,.,$(PACKAGE_RACINE))

getjar:
	cd $(CLASSES_DEST); jar cfm vehicleInventory.jar ../../$(MANIFEST) main/
	mkdir -p $(DIST)
	mv $(CLASSES_DEST)/vehicleInventory.jar $(DIST)

test:
	java -cp $(CLASSES_DEST_TEST):$(CLASSES_DEST):$(LIB)/junit-4.13.2.jar:$(LIB)/hamcrest-core-1.3.jar org.junit.runner.JUnitCore $(subst /,.,$(PACKAGE_RACINE)).VehicleDAOTest

run:
	java -classpath $(CLASSES_DEST) $(subst /,.,$(PACKAGE_RACINE)).Main
# We can also use "java -jar $(DIST)/vehicleInventory.jar"

clean:
	rm -rf $(CLASSES_DEST)/*
	rm -rf $(CLASSES_DEST_TEST)/*
	rm -rf $(DOC)/*
	rm -rf $(DIST)/vehicleInventory.jar
