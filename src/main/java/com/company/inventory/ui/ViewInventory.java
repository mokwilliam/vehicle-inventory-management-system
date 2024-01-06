package com.company.inventory.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.company.inventory.dao.VehicleDAO;

/**
 * Represents the view inventory panel
 * 
 * @see JPanel
 */
public class ViewInventory extends JPanel {
    // Header JTable
    protected String[] columns = new String[] {
            "Brand",
            "Model",
            "Date of Manufacture",
            "Color",
            "License Plate",
            "Price",
            "Status",
            "Fuel Type",
            "Kilometers",
            "Actions"
    };

    // Initialize the DAO
    protected VehicleDAO vehicleDAO = new VehicleDAO();

    // Create the buttons
    private JButton btnAdd, btnEdit, btnRemove;

    // Default data for the table
    protected Object[][] data = new Object[][] {
            { "Toyota", "Camry", "2019-01-01", "White", "ABC123", 59999.99, "AVAILABLE", "HYBRID", 168712, btnRemove },
            { "BMW", "M3", "2019-01-01", "Black", "DEF456", 100000, "AVAILABLE", "PETROL", 98432, btnRemove }
    };

    // Create the table
    protected JTable inventoryTable = new JTable(data, columns);
    protected TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(inventoryTable.getModel());
    protected JTextField textField = new JTextField();

    /**
     * Constructs the view inventory panel
     */
    public ViewInventory() {
        // Set the sorter for the table
        inventoryTable.setRowSorter(sorter);

        // Add ActionListeners to the buttons
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnRemove = new JButton("Remove");
        btnAdd.addActionListener(new AddVehicleListener());
        btnEdit.addActionListener(new EditVehicleListener());
        btnRemove.addActionListener(new RemoveVehicleListener());

        // Create the search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Look for a vehicle: "), BorderLayout.WEST);
        searchPanel.add(textField, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(btnAdd, BorderLayout.CENTER);
        southPanel.add(searchPanel, BorderLayout.SOUTH);

        // Add the panels to the view inventory panel
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        // Add a listener to the text field to change the filter
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                String str = textField.getText();
                if (str.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) recherche insensible à la casse
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String str = textField.getText();
                if (str.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + str));
                }
            }
        });
    }

    public class AddVehicleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Open a form to add a vehicle
        }
    }

    public class EditVehicleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the selected row
            int selectedRow = inventoryTable.getSelectedRow();

            // Get the license plate of the vehicle to edit
            String licensePlate = (String) inventoryTable.getValueAt(selectedRow, 4);
        }
    }

    public class RemoveVehicleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the selected row
            int selectedRow = inventoryTable.getSelectedRow();

            // Get the license plate of the vehicle to remove
            String licensePlate = (String) inventoryTable.getValueAt(selectedRow, 4);

            // Remove the vehicle from the database
            vehicleDAO.removeVehicle(licensePlate);

            // Remove the vehicle from the table
            ((DefaultTableModel) inventoryTable.getModel()).removeRow(selectedRow);
        }
    }
}
