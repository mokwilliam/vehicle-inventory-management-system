SRC_MAIN=src/main/java
SRC_TEST=src/test/java
CLASSES_DEST=target/classes
DIST=dist
DOC=docs
MANIFEST=MANIFEST.MF

PACKAGE_RACINE=com/company/inventory

all: compile docs getjar

compile:
	javac -cp $(CLASSES_DEST) -sourcepath $(SRC_MAIN) -d $(CLASSES_DEST) $(SRC_MAIN)/$(PACKAGE_RACINE)/Main.java

docs:
	javadoc -cp $(CLASSES_DEST) -sourcepath $(SRC_MAIN) -d $(DOC) -subpackages $(subst /,.,$(PACKAGE_RACINE))

getjar:
	cd $(CLASSES_DEST); jar cfm vehicleInventory.jar ../../$(MANIFEST) main/
	mkdir -p $(DIST)
	mv $(CLASSES_DEST)/vehicleInventory.jar $(DIST)

run:
	java -classpath $(CLASSES_DEST) $(subst /,.,$(PACKAGE_RACINE)).Main
# We can also use "java -jar $(DIST)/vehicleInventory.jar"

clean:
	rm -rf $(CLASSES_DEST)/*
	rm -rf $(DOC)/*
	rm -rf $(DIST)/vehicleInventory.jar