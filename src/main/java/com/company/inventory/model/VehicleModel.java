package com.company.inventory.model;

import java.time.LocalDate;

/**
 * Represents a vehicle with a brand, model, date of manufacture, color,
 * license plate, price, status, fuel type and kilometers
 */
public class VehicleModel {
    
    private String brand;
    private String model;
    private LocalDate dateOfManufacture;
    private String color;
    private String licensePlate;
    private float price;
    private Status status;
    private FuelType fuelType;
    private float kms;
    
    /**
     * Status enum for vehicle
     */
    public enum Status {
        AVAILABLE, SOLD
    }

    /**
     * FuelType enum for vehicle
     */
    public enum FuelType {
        PETROL, DIESEL, ELECTRIC, HYBRID
    }

    /**
     * Constructs a vehicle with a brand, model, date of manufacture, color,
     * license plate, price, status, fuel type and kilometers
     * 
     * @param brand the brand
     * @param model the model
     * @param dateOfManufacture the date of manufacture
     * @param color the color
     * @param licensePlate the license plate
     * @param price the price
     * @param status the status
     * @param fuelType the fuel type
     * @param kms the kilometers
     */
    public VehicleModel(String brand, String model, LocalDate dateOfManufacture, String color,
                        String licensePlate, float price, Status status, FuelType fuelType, float kms) {
        this.brand = brand;
        this.model = model;
        this.dateOfManufacture = dateOfManufacture;
        this.color = color;
        this.licensePlate = licensePlate;
        this.price = price;
        this.status = status;
        this.fuelType = fuelType;
        this.kms = kms;
    }

}