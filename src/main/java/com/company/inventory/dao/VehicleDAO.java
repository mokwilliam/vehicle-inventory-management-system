package com.company.inventory.dao;

import java.time.LocalDate;
import java.util.HashMap;

import com.company.inventory.model.VehicleModel;
import com.company.inventory.model.VehicleModel.FuelType;
import com.company.inventory.model.VehicleModel.Status;

/**
 * Represents the vehicle Digital Access Object (DAO)
 */
public class VehicleDAO {

    private HashMap<String, VehicleModel> carDatabase;
    private int id = 1;

    /**
     * Constructs a vehicle Digital Access Object (DAO)
     */
    public VehicleDAO() {
        this.carDatabase = new HashMap<String, VehicleModel>();
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
     * Adds a vehicle to the database
     * 
     * @param vehicle the vehicle to add
     */
    public void addVehicle(Object[] rowData) {
        VehicleModel vehicle = formatVehicleModel(rowData);
        if (vehicle.licensePlate == null || vehicle.licensePlate.isEmpty()) {
            vehicle.licensePlate = "ABC" + id;
            id++;
        }
        while (this.carDatabase.containsKey(vehicle.licensePlate)) {
            vehicle.licensePlate += id;
            id++;
        }
        // System.out.println("Adding vehicle with licence plate " +
        // vehicle.licensePlate);
        this.carDatabase.put(vehicle.licensePlate, vehicle);
        displayDatabase();
    }

    /**
     * Updates a vehicle from the database
     * 
     * @param rowData
     */
    public void updateVehicle(Object[] rowData) {
        VehicleModel vehicle = formatVehicleModel(rowData);
        // System.out.println("Updating vehicle with licence plate " +
        // vehicle.licensePlate);
        this.carDatabase.put(vehicle.licensePlate, vehicle);
        displayDatabase();
    }

    /**
     * Removes a vehicle from the database
     * 
     * @param licensePlate the license plate of the vehicle to remove
     */
    public void removeVehicle(String licensePlate) {
        // System.out.println("Removing vehicle with license plate " + licensePlate);
        this.carDatabase.remove(licensePlate);
        displayDatabase();
    }

}