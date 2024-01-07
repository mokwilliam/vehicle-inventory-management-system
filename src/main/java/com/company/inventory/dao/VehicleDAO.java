package com.company.inventory.dao;

import java.util.HashMap;

import com.company.inventory.model.VehicleModel;

/**
 * Represents the vehicle Digital Access Object (DAO)
 */
public class VehicleDAO {

    private HashMap<String, VehicleModel> carDatabase;

    /**
     * Constructs a vehicle Digital Access Object (DAO)
     */
    public VehicleDAO() {
        this.carDatabase = new HashMap<String, VehicleModel>();
    }

    /**
     * Retrieves a vehicle from the database by license plate
     * 
     * @param licensePlate the license plate
     * @return the vehicle
     */
    public VehicleModel getVehicleBylicensePlate(String licensePlate) {
        // Retrieve vehicle from database
        return this.carDatabase.get(licensePlate);
    }

    /**
     * Adds a vehicle to the database
     * 
     * @param vehicle the vehicle to add
     */
    public void addVehicle(VehicleModel vehicle) {
        // Add vehicle to database
        this.carDatabase.put(vehicle.licensePlate, vehicle);
    }

    public void updateVehicle(VehicleModel vehicle) {
        // Update vehicle in database
        this.carDatabase.put(vehicle.licensePlate, vehicle);
    }

    /**
     * Removes a vehicle from the database
     * 
     * @param licensePlate the license plate of the vehicle to remove
     */
    public void removeVehicle(String licensePlate) {
        // Remove vehicle from database
        this.carDatabase.remove(licensePlate);
    }

}