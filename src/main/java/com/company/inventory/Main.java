package com.company.inventory;

import javax.swing.JFrame;

import com.company.inventory.model.VehicleModel;
import com.company.inventory.ui.AuthenticationDialog;
import com.company.inventory.ui.MainFrame;

/**
 * Main class
 * 
 * @author mokwilliam
 * 
 * @see VehicleModel
 */
public class Main {

    /**
     * Main method
     * 
     * @param args the command line arguments
     */
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
     * @see AuthenticationDialog
     * @see MainFrame
     */
    public void start() {
        // Initialize and launch the application
        authenticateAndDisplay();
    }

    private void authenticateAndDisplay() {
        JFrame authframe = new JFrame();
        AuthenticationDialog authForm = new AuthenticationDialog(authframe);
        boolean isAdmin = authForm.isAdmin();

        if (!authForm.actionPerformed) {
            authForm.dispose();
            System.exit(0);
        }
        MainFrame mainFrame = new MainFrame(isAdmin);
        mainFrame.display();
    }

}