package com.company.inventory.dao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.company.inventory.model.VehicleModel;
import com.company.inventory.model.VehicleModel.FuelType;
import com.company.inventory.model.VehicleModel.Status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents the vehicle Digital Access Object (DAO)
 */
public class VehicleDAO {

    private Map<String, VehicleModel> carDatabase;
    private static final String INVENTORY_DB = "src/main/resources/db/inventory.json";

    /**
     * Constructs a vehicle Digital Access Object (DAO)
     */
    public VehicleDAO() {
        this.carDatabase = new LinkedHashMap<String, VehicleModel>();
        initDefaultInventory();

        File inventoryFile = new File(INVENTORY_DB);
        if (!inventoryFile.exists()) {
            try {
                inventoryFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadInventoryFromFile(inventoryFile);
            System.out.println("Loading inventory from file " + INVENTORY_DB);
        }
    }

    /**
     * Constructs a vehicle Digital Access Object (DAO) with the specified test
     * 
     * @param test whether it is a test
     */
    public VehicleDAO(boolean test) {
        this.carDatabase = new LinkedHashMap<String, VehicleModel>();
        if (test) {
            initDefaultInventory();
        }
    }

    /**
     * Formats the data from the table to a VehicleModel
     * 
     * @param rowData the data from the table
     * @return the VehicleModel
     */
    private VehicleModel formatVehicleModel(Object[] rowData) {
        return new VehicleModel(
                rowData[4].toString(),
                rowData[0].toString(),
                rowData[1].toString(),
                LocalDate.parse(rowData[2].toString()),
                rowData[3].toString(),
                Float.valueOf(rowData[5].toString()),
                Status.valueOf(rowData[6].toString()),
                FuelType.valueOf(rowData[7].toString()),
                Float.valueOf(rowData[8].toString()));
    }

    /**
     * Initializes the default inventory
     */
    private void initDefaultInventory() {
        if (!this.carDatabase.containsKey("ABC123"))
            this.carDatabase.put("ABC123", formatVehicleModel(new Object[] {
                    "Toyota",
                    "Camry",
                    "2019-01-01",
                    "White",
                    "ABC123",
                    "59999.99",
                    "AVAILABLE",
                    "HYBRID",
                    "168712"
            }));
        if (!this.carDatabase.containsKey("DEF456"))
            this.carDatabase.put("DEF456", formatVehicleModel(new Object[] {
                    "BMW",
                    "M3",
                    "2019-01-01",
                    "Black",
                    "DEF456",
                    "100000",
                    "AVAILABLE",
                    "PETROL",
                    "98432"
            }));
    }

    /**
     * Loads the inventory from the database
     * 
     * @param inventoryFile the inventory file
     */
    private void loadInventoryFromFile(File inventoryFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Map<String, String>> loadedData = objectMapper.readValue(
                    inventoryFile,
                    new TypeReference<Map<String, Map<String, String>>>() {
                    });

            for (Map.Entry<String, Map<String, String>> entry : loadedData.entrySet()) {
                String licensePlate = entry.getKey();
                Map<String, String> vehicleData = entry.getValue();

                VehicleModel vehicleModel = new VehicleModel(
                        vehicleData.get("licensePlate"),
                        vehicleData.get("brand"),
                        vehicleData.get("model"),
                        LocalDate.parse(vehicleData.get("dateOfManufacture")),
                        vehicleData.get("color"),
                        Float.parseFloat(vehicleData.get("price")),
                        Status.valueOf(vehicleData.get("status")),
                        FuelType.valueOf(vehicleData.get("fuelType")),
                        Float.parseFloat(vehicleData.get("kms")));

                this.carDatabase.put(licensePlate, vehicleModel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the inventory to the database
     */
    private void saveInventory() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            // Configure the ObjectMapper to include indentation in JSON output
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(INVENTORY_DB),
                    convertToSerializedMap(this.carDatabase));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the inventory to the database on exit
     */
    public void saveDataOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveInventory));
    }

    /**
     * Converts the vehicle database to a serialized map
     * 
     * @param vehicleModelMap the vehicle database
     * @return the serialized map
     */
    private Map<String, Map<String, String>> convertToSerializedMap(Map<String, VehicleModel> vehicleModelMap) {
        Map<String, Map<String, String>> serializedMap = new LinkedHashMap<>();

        for (Map.Entry<String, VehicleModel> entry : vehicleModelMap.entrySet()) {
            String licensePlate = entry.getKey();
            VehicleModel vehicleModel = entry.getValue();

            Map<String, String> data = new LinkedHashMap<>();
            data.put("licensePlate", vehicleModel.licensePlate);
            data.put("brand", vehicleModel.brand);
            data.put("model", vehicleModel.model);
            data.put("dateOfManufacture", vehicleModel.dateOfManufacture.toString());
            data.put("color", vehicleModel.color);
            data.put("price", String.valueOf(vehicleModel.price));
            data.put("status", vehicleModel.status.toString());
            data.put("fuelType", vehicleModel.fuelType.toString());
            data.put("kms", String.valueOf(vehicleModel.kms));

            serializedMap.put(licensePlate, data);
        }

        System.out.println(serializedMap);
        return serializedMap;
    }

    /**
     * Displays all vehicles from the database
     */
    public void displayDatabase() {
        System.out.println("Now, the database looks like:");
        this.carDatabase.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
    }

    /**
     * Retrieves all vehicles from the database
     * 
     * @return the vehicles
     */
    public Object[][] getVehicles() {
        Object[][] vehicles = new Object[this.carDatabase.size()][];
        int i = 0;
        for (VehicleModel vehicle : this.carDatabase.values()) {
            vehicles[i] = new Object[] {
                    vehicle.brand,
                    vehicle.model,
                    vehicle.dateOfManufacture,
                    vehicle.color,
                    vehicle.licensePlate,
                    String.format("%.2f", vehicle.price),
                    vehicle.status,
                    vehicle.fuelType,
                    String.format("%.2f", vehicle.kms),
                    ""
            };
            i++;
        }
        return vehicles;
    }

    /**
     * Retrieves a vehicle from the database by license plate
     * 
     * @param licensePlate the license plate
     * @return the vehicle
     */
    public VehicleModel getVehicleBylicensePlate(String licensePlate) {
        return this.carDatabase.get(licensePlate);
    }

    /**
     * Updates a vehicle from the database
     * 
     * @param row     the row to update
     * @param rowData the data to update
     */
    public void updateVehicle(int row, Object[] rowData) {

        VehicleModel vehicle = formatVehicleModel(rowData);
        int newId = 1;

        // Generate a default license plate if it's empty or null
        if (vehicle.licensePlate == null || vehicle.licensePlate.isEmpty()) {
            vehicle.licensePlate = "ABC" + newId++;
        }

        List<String> plates = new ArrayList<>(this.carDatabase.keySet());

        if (row == -1) {
            if (!this.carDatabase.containsKey(vehicle.licensePlate)) {
                // If it's a new row and the license plate doesn't exist, add the vehicle
                this.carDatabase.put(vehicle.licensePlate, vehicle);
            } else {
                // If the plate exists, find a unique ID and add the vehicle with the new plate
                while (this.carDatabase.containsKey(vehicle.licensePlate + newId)) {
                    newId++;
                }
                vehicle.licensePlate += newId;
                this.carDatabase.put(vehicle.licensePlate, vehicle);
            }
        } else {
            String currentPlate = plates.get(row);
            if (!this.carDatabase.containsKey(vehicle.licensePlate)) {
                // If the plate doesn't exist, remove the old plate and add the new vehicle
                this.carDatabase.remove(currentPlate);
                this.carDatabase.put(vehicle.licensePlate, vehicle);
            } else {
                if (this.carDatabase.containsKey(vehicle.licensePlate)
                        && currentPlate.equals(vehicle.licensePlate)) {
                    // If the plate exists and it matches the plate at the row, update the vehicle
                    this.carDatabase.put(vehicle.licensePlate, vehicle);
                } else {
                    // If the plate exists but doesn't match the plate at the row, find a unique ID
                    // and add the vehicle with the new plate
                    while (this.carDatabase.containsKey(vehicle.licensePlate + newId)
                            && !currentPlate.equals(vehicle.licensePlate)) {
                        newId++;
                    }
                    vehicle.licensePlate += newId;
                    this.carDatabase.remove(currentPlate);
                    this.carDatabase.put(vehicle.licensePlate, vehicle);
                }
            }
        }
    }

    /**
     * Removes a vehicle from the database
     * 
     * @param licensePlate the license plate of the vehicle to remove
     */
    public void removeVehicle(String licensePlate) {
        this.carDatabase.remove(licensePlate);
    }

}