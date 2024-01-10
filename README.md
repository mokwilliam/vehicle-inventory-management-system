# Vehicle Inventory Management System

This small project focuses on creating a Vehicle Inventory Management System. The goal is to build a structured dataset containing vehicle information such as make, model, year, and price. The project architecture is based on Java Spring Boot.

## Help from:

- [JDK 21](https://docs.oracle.com/en/java/javase/21/)
- [JDK 21 API](https://docs.oracle.com/en/java/javase/21/docs/api/index.html)
- ChatGPT

## Overview

The Vehicle Inventory Management System is a Java-based software designed for a vehicle selling company. It serves as a centralized repository to manage and store information about cars available for sale. The system allows employees to perform various actions such as adding new cars, removing existing cars, and querying the inventory based on specific criteria.

## Steps followed

### 0. Installation & Setup

The JAR files retrieved and stored in the `lib` folder are:

- [Jackson core](https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.14.0/jackson-core-2.14.0.jar)
- [Jackson annotations](https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.14.0/jackson-annotations-2.14.0.jar)
- [Jackson databind](https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.14.0/jackson-databind-2.14.0.jar)
- [JUnit](https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar)
- [Hamcrest](https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar)

On Windows, I use the `make_win.sh` script to compile and run the application. On Linux, we can use the `Makefile`.

### 1. Project architecture

The modules (packages) are organized as follows:

- `com.company.inventory`: contains the main class of the application
- `com.company.inventory.dao`: contains the data access object classes interacting with the database
- `com.company.inventory.model`: contains the model classes defining the data structure
- `com.company.inventory.ui`: contains the user interface classes handling user interface components

### 2. Running the application

The application can be run using the following commands present in the `make_win.sh` script (for Windows) or in the `Makefile` (for Linux):

- For Makefile:

```bash
# To compile everything
make # or make all ()

# To run the application
make run
```

- For make_win.sh:

```bash
# To compile everything
make_win.sh

# To run the application
make_win.sh run
```

On Docker (**compile before to get jar file**), we can use the following commands:

```bash
# Docker compose
docker-compose up

# To build the image
docker build -t vehicle-inventory-app .

# To run the container
docker run -it --rm --name vehicle-inventory-running-app vehicle-inventory-app

# To remove the image
docker rmi vehicle-inventory-app
```

### Interesting points / Issues I encountered

- On the UI side, I had to look carefully at the Java Swing documentation to understand how to use the different components and how to handle events.
- I didn't know why when I clicked on the "Modify/Remove" button, the action continued to be performed on the following rows. I finally understood that ActionListener should have been specified in the constructor of the button.
- Struggled a bit with the JSON serialization/deserialization. I had to look at the Jackson documentation to understand how to use the ObjectMapper class.
- I past a long time on the updateVehicle method. Indeed, there are several cases to consider so are the different manipulations to perform.
- To see the content of a JAR file, we can use the command `jar tf <jar-file>`.
- On Windows, to separate the different paths in the classpath, we use the semicolon (;) instead of the colon (:) on Linux !

### Possible improvements

- Use a database to store the data instead of a JSON file
- The authentication system is not very secure. We could use a more robust system (instead of writing the password in the code).

### Extra: Setup of Makefile

```bash
# To compile everything
make # or make all

# To run the application
make run

# To run tests
make test

# To clean
make clean
```
