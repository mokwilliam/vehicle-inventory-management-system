import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeEach;
import org.junit.Test;

import com.company.inventory.dao.VehicleDAO;
import com.company.inventory.model.VehicleModel;

public class VehicleDAOTest {
    private VehicleDAO vehicleDAO;

    @BeforeEach
    public void setUp() {
        vehicleDAO = new VehicleDAO();
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
}