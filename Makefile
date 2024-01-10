# Variables
SRC_MAIN=src/main/java
SRC_TEST=src/test/java
CLASSES_DEST=target/classes
CLASSES_DEST_TEST=target/test_classes
DIST=dist
LIB=lib
DOC=docs
MANIFEST=MANIFEST.MF

PACKAGE_RACINE=com/company/inventory

CLASSPATH=$(CLASSES_DEST):$(LIB)/jackson-core-2.14.0.jar:$(LIB)/jackson-databind-2.14.0.jar:$(LIB)/jackson-annotations-2.14.0.jar

all: compile compile-test docs getjar

compile:
	@echo "Compile the application"
	@javac -cp $(CLASSPATH) -sourcepath $(SRC_MAIN) -d $(CLASSES_DEST) $(SRC_MAIN)/$(PACKAGE_RACINE)/Main.java

compile-test:
	@echo "Compile the tests"
	@javac -cp $(CLASSES_DEST_TEST):$(CLASSPATH):$(LIB)/junit-4.13.2.jar -sourcepath $(SRC_TEST) -d $(CLASSES_DEST_TEST) $(SRC_TEST)/$(PACKAGE_RACINE)/VehicleDAOTest.java

extracted-classes:
	@mkdir -p tmp_extracted_classes
	@if [ ! -d tmp_extracted_classes/com ]; then \
		cd tmp_extracted_classes && \
		jar xf "../$(LIB)/jackson-core-2.14.0.jar" com/fasterxml/jackson/core && \
		jar xf "../$(LIB)/jackson-databind-2.14.0.jar" com/fasterxml/jackson/databind && \
		jar xf "../$(LIB)/jackson-annotations-2.14.0.jar" com/fasterxml/jackson/annotation && \
		cd ..; \
	fi

docs:	extracted-classes
	@echo "Generate the documentation"
	@javadoc -cp $(CLASSPATH):tmp_extracted_classes -sourcepath "$(SRC_MAIN)" -d "$(DOC)" -subpackages $(subst /,.,$(PACKAGE_RACINE))

getjar:	extracted-classes
	@echo "Get the jar file"
	@mkdir -p dist
	@jar cfm "$(DIST)/vehicleInventory.jar" "$(MANIFEST)" -C "$(CLASSES_DEST)" . -C tmp_extracted_classes .

test:
	@echo "Run the tests"
	java -cp $(CLASSES_DEST_TEST):$(CLASSPATH):$(LIB)/junit-4.13.2.jar:$(LIB)/hamcrest-core-1.3.jar org.junit.runner.JUnitCore $(subst /,.,$(PACKAGE_RACINE)).VehicleDAOTest

run:
	@echo "Run the application"
	java -classpath $(CLASSPATH) $(subst /,.,$(PACKAGE_RACINE)).Main
# We can also use "java -jar $(DIST)/vehicleInventory.jar"

clean:
	@echo "Clean the directories"
	rm -rf $(CLASSES_DEST)
	rm -rf $(CLASSES_DEST_TEST)
	rm -rf $(DOC)
	rm -rf $(DIST)/vehicleInventory.jar
