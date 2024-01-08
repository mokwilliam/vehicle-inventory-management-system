package com.company.inventory.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.company.inventory.model.VehicleModel;
import com.company.inventory.model.VehicleModel.FuelType;
import com.company.inventory.model.VehicleModel.Status;

/**
 * Represents the vehicle Digital Access Object (DAO)
 */
public class _VehicleDAO {

    private static final String DB_URL = "jdbc:sqlite:src\\main\\ressources\\db\\inventory.db";
    private Connection connection;

    /**
     * Constructs a vehicle Digital Access Object (DAO)
     * 
     * @throws SQLException
     */
    public _VehicleDAO() {
        initDatabase();
    }

    private void initDatabase() {
        try {

            // Connect to the SQLite database
            connection = DriverManager.getConnection(DB_URL);

            // Create the table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS vehicles ("
                    + "licensePlate TEXT PRIMARY KEY,"
                    + "brand TEXT,"
                    + "model TEXT,"
                    + "dateOfManufacture TEXT,"
                    + "color TEXT,"
                    + "price REAL,"
                    + "status TEXT,"
                    + "fuelType TEXT,"
                    + "kms REAL)";
            connection.createStatement().executeUpdate(createTableSQL);

            // Insert some data if the table is empty
            String insertDataSQL = "INSERT INTO vehicles VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertDataSQL)) {
                pstmt.setString(1, "ABC123");
                pstmt.setString(2, "Toyota");
                pstmt.setString(3, "Camry");
                pstmt.setString(4, "2019-01-01");
                pstmt.setString(5, "White");
                pstmt.setFloat(6, 59999.99f);
                pstmt.setString(7, "AVAILABLE");
                pstmt.setString(8, "HYBRID");
                pstmt.setFloat(9, 168712);
                pstmt.executeUpdate();

                pstmt.setString(1, "DEF456");
                pstmt.setString(2, "BMW");
                pstmt.setString(3, "M3");
                pstmt.setString(4, "2019-01-01");
                pstmt.setString(5, "Black");
                pstmt.setFloat(6, 100000);
                pstmt.setString(7, "AVAILABLE");
                pstmt.setString(8, "PETROL");
                pstmt.setFloat(9, 98432);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Retrieves all vehicles from the database
     * 
     * @return the vehicles
     */
    public Object[][] getVehicles() {
        List<Object[]> vehicles = new ArrayList<Object[]>();
        try {
            PreparedStatement selectStatement = connection
                    .prepareStatement("SELECT * FROM vehicles");
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Object[] vehicle = new Object[] {
                        resultSet.getString("licensePlate"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getString("dateOfManufacture"),
                        resultSet.getString("color"),
                        resultSet.getFloat("price"),
                        resultSet.getString("status"),
                        resultSet.getString("fuelType"),
                        resultSet.getFloat("kms")
                };
                vehicles.add(vehicle);
            }
            return vehicles.toArray(new Object[vehicles.size()][9]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves a vehicle from the database by license plate
     * 
     * @param licensePlate the license plate
     * @return the vehicle
     */
    public VehicleModel getVehicleBylicensePlate(String licensePlate) {
        try {
            PreparedStatement selectStatement = connection
                    .prepareStatement("SELECT * FROM vehicles WHERE licensePlate=?");
            selectStatement.setString(1, licensePlate);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Object[] vehicle = new Object[] {
                        resultSet.getString("licensePlate"),
                        resultSet.getString("brand"),
                        resultSet.getString("model"),
                        resultSet.getString("dateOfManufacture"),
                        resultSet.getString("color"),
                        resultSet.getFloat("price"),
                        resultSet.getString("status"),
                        resultSet.getString("fuelType"),
                        resultSet.getFloat("kms")
                };
                return formatVehicleModel(vehicle);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Adds a vehicle to the database
     * 
     * @param vehicle the vehicle to add
     */
    public void addVehicle(Object[] rowData) {
        VehicleModel vehicle = formatVehicleModel(rowData);
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO vehicles"
                            + "(licensePlate, brand, model, dateOfManufacture, color,"
                            + "price, status, fuelType, kms)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insertStatement.setString(1, vehicle.licensePlate);
            insertStatement.setString(2, vehicle.brand);
            insertStatement.setString(3, vehicle.model);
            insertStatement.setString(4, vehicle.dateOfManufacture.toString());
            insertStatement.setString(5, vehicle.color);
            insertStatement.setFloat(6, vehicle.price);
            insertStatement.setString(7, vehicle.status.toString());
            insertStatement.setString(8, vehicle.fuelType.toString());
            insertStatement.setFloat(9, vehicle.kms);

            insertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVehicle(Object[] rowData) {
        VehicleModel vehicle = formatVehicleModel(rowData);
        try {
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE vehicles SET brand=?, model=?, dateOfManufacture=?, color=?,"
                            + "price=?, status=?, fuelType=?, kms=? WHERE licensePlate=?");
            updateStatement.setString(1, vehicle.brand);
            updateStatement.setString(2, vehicle.model);
            updateStatement.setString(3, vehicle.dateOfManufacture.toString());
            updateStatement.setString(4, vehicle.color);
            updateStatement.setFloat(5, vehicle.price);
            updateStatement.setString(6, vehicle.status.toString());
            updateStatement.setString(7, vehicle.fuelType.toString());
            updateStatement.setFloat(8, vehicle.kms);
            updateStatement.setString(9, vehicle.licensePlate);

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a vehicle from the database
     * 
     * @param licensePlate the license plate of the vehicle to remove
     */
    public void removeVehicle(String licensePlate) {
        try {
            PreparedStatement removeStatement = connection
                    .prepareStatement("DELETE FROM vehicles WHERE licensePlate=?");
            removeStatement.setString(1, licensePlate);
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}