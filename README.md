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

### 1. Project architecture

The modules (packages) are organized as follows:

- `com.company.inventory`: contains the main class of the application
- `com.company.inventory.dao`: contains the data access object classes interacting with the database
- `com.company.inventory.model`: contains the model classes defining the data structure
- `com.company.inventory.ui`: contains the user interface classes handling user interface components

### Interesting points / Issues I encountered

- On the UI side, I had to look carefully at the Java Swing documentation to understand how to use the different components and how to handle events.

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
