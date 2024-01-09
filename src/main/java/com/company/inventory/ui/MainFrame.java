package com.company.inventory.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * Represents the main frame of the application
 */
public class MainFrame extends JFrame {

    /**
     * Constructs a main frame with the specified isAdmin
     * 
     * @param isAdmin whether the user is an admin
     */
    public MainFrame(boolean isAdmin) {
        // Create the main frame and the panels
        this.setTitle("Vehicle Inventory Management System");
        this.add(new ViewInventory(isAdmin));
        this.setSize(800, 600);
        this.setPreferredSize(new Dimension(800, 600));
        // this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    /**
     * Displays the main frame
     */
    public void display() {
        // Display the main frame
        this.setVisible(true);
    }

}
