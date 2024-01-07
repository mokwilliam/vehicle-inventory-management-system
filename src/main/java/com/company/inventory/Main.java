package com.company.inventory;

import com.company.inventory.model.VehicleModel;
import com.company.inventory.ui.MainFrame;

/**
 * Main class
 * 
 * @author mokwilliam
 * 
 * @see VehicleModel
 */
public class Main {

    public static void main(String[] args) {
        VehicleInventoryApp vehicleInventoryApp = new VehicleInventoryApp();
        vehicleInventoryApp.start();
    }

}

/**
 * Represents the vehicle inventory application
 */
class VehicleInventoryApp {

    /**
     * Starts the application
     * 
     * @see MainFrame
     */
    public void start() {
        // Initialize and launch the application
        MainFrame mainFrame = new MainFrame();
        mainFrame.display();
    }

}