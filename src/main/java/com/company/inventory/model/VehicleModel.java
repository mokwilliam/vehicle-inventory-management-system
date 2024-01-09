package com.company.inventory.model;

import java.time.LocalDate;

/**
 * Represents a vehicle with a brand, model, date of manufacture, color,
 * license plate, price, status, fuel type and kilometers
 */
public class VehicleModel {

    /**
     * The license plate
     */
    public String licensePlate;

    /**
     * The brand
     */
    public String brand;

    /**
     * The model
     */
    public String model;

    /**
     * The date of manufacture
     */
    public LocalDate dateOfManufacture;

    /**
     * The color
     */
    public String color;

    /**
     * The price
     */
    public float price;

    /**
     * The status
     */
    public Status status;

    /**
     * The fuel type
     */
    public FuelType fuelType;

    /**
     * The kilometers
     */
    public float kms;

    /**
     * Status enum for vehicle
     * 
     * {@link Status#AVAILABLE}
     * {@link Status#SOLD}
     */
    public enum Status {
        /**
         * Available status
         */
        AVAILABLE,
        /**
         * Sold status
         */
        SOLD
    }

    /**
     * FuelType enum for vehicle
     * 
     * {@link FuelType#PETROL}
     * {@link FuelType#DIESEL}
     * {@link FuelType#ELECTRIC}
     * {@link FuelType#HYBRID}
     */
    public enum FuelType {
        /**
         * Petrol fuel type
         */
        PETROL,
        /**
         * Diesel fuel type
         */
        DIESEL,
        /**
         * Electric fuel type
         */
        ELECTRIC,
        /**
         * Hybrid fuel type
         */
        HYBRID
    }

    /**
     * Constructs a vehicle with a brand, model, date of manufacture, color,
     * license plate, price, status, fuel type and kilometers
     * 
     * @param licensePlate      the license plate
     * @param brand             the brand
     * @param model             the model
     * @param dateOfManufacture the date of manufacture
     * @param color             the color
     * @param price             the price
     * @param status            the status
     * @param fuelType          the fuel type
     * @param kms               the kilometers
     */
    public VehicleModel(String licensePlate, String brand, String model, LocalDate dateOfManufacture, String color,
            float price, Status status, FuelType fuelType, float kms) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.model = model;
        this.dateOfManufacture = dateOfManufacture;
        this.color = color;
        this.price = price;
        this.status = status;
        this.fuelType = fuelType;
        this.kms = kms;
    }

    @Override
    public String toString() {
        return "VehicleModel [brand=" + brand + ", model=" + model + ", dateOfManufacture=" + dateOfManufacture
                + ", color=" + color + ", licensePlate=" + licensePlate + ", price=" + price + ", status=" + status
                + ", fuelType=" + fuelType + ", kms=" + kms + "]";
    }

}