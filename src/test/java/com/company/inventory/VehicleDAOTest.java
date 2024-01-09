package com.company.inventory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.company.inventory.dao.VehicleDAO;
import com.company.inventory.model.VehicleModel;

public class VehicleDAOTest {
    private VehicleDAO vehicleDAO;
    private static final boolean TEST = true;

    @Before
    public void setUp() {
        vehicleDAO = new VehicleDAO(TEST);
    }

    @Test
    public void testGetVehicles() {
        Object[][] vehicles = vehicleDAO.getVehicles();
        assertEquals(2, vehicles.length);
        assertEquals("ABC123", vehicles[0][4]);
        assertEquals("DEF456", vehicles[1][4]);
    }

    @Test
    public void testGetVehicleBylicensePlate() {
        VehicleModel vehicle = vehicleDAO.getVehicleBylicensePlate("ABC123");
        assertNotNull(vehicle);
        assertEquals("Toyota", vehicle.brand);
        assertEquals("Camry", vehicle.model);
    }

    @Test
    public void testAddNewVehicle() {
        Object[][] initialVehicles = vehicleDAO.getVehicles();
        int initialCount = initialVehicles.length;

        Object[] newVehicleData = {
                "Nissan",
                "Altima",
                "2022-01-01",
                "Silver",
                "GHI789",
                "35000.00",
                "AVAILABLE",
                "PETROL",
                "10000"
        };

        vehicleDAO.updateVehicle(-1, newVehicleData);

        Object[][] updatedVehicles = vehicleDAO.getVehicles();
        int updatedCount = updatedVehicles.length;

        assertEquals(initialCount + 1, updatedCount);

        // Verify if the newly added vehicle exists
        boolean exists = false;
        for (Object[] vehicle : updatedVehicles) {
            if (vehicle[4].equals("GHI789")) {
                exists = true;
                break;
            }
        }
        assertTrue(exists);
    }

    @Test
    public void testUpdateVehicle() {
        Object[][] initialVehicles = vehicleDAO.getVehicles();
        int initialCount = initialVehicles.length;

        Object[] updatedVehicleData = {
                "Toyota",
                "Camry",
                "2020-01-01",
                "Red",
                "ABC123",
                "65000.00",
                "SOLD",
                "HYBRID",
                "20000"
        };

        vehicleDAO.updateVehicle(0, updatedVehicleData);

        Object[][] updatedVehicles = vehicleDAO.getVehicles();
        int updatedCount = updatedVehicles.length;

        assertEquals(initialCount, updatedCount);

        // Verify if the vehicle details have been updated
        VehicleModel updatedVehicle = vehicleDAO.getVehicleBylicensePlate("ABC123");
        assertNotNull(updatedVehicle);
        assertEquals("Red", updatedVehicle.color);
        assertEquals("SOLD", updatedVehicle.status.toString());
        assertEquals(20000, updatedVehicle.kms, 0.01);
    }

    @Test
    public void testRemoveVehicle() {
        Object[][] initialVehicles = vehicleDAO.getVehicles();
        int initialCount = initialVehicles.length;

        vehicleDAO.removeVehicle("ABC123");

        Object[][] updatedVehicles = vehicleDAO.getVehicles();
        int updatedCount = updatedVehicles.length;

        assertEquals(initialCount - 1, updatedCount);

        // Verify that the removed vehicle does not exist in the updated database
        VehicleModel removedVehicle = vehicleDAO.getVehicleBylicensePlate("ABC123");
        assertNull(removedVehicle);
    }

}