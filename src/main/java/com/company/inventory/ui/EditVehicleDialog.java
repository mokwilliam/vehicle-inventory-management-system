package com.company.inventory.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.company.inventory.model.VehicleModel.FuelType;
import com.company.inventory.model.VehicleModel.Status;
import com.company.inventory.ui.ViewInventory.MyTableModel;

/**
 * Represents the edit vehicle dialog
 * 
 * @see JDialog
 */
public class EditVehicleDialog extends JDialog {

    /**
     * The row data
     */
    private Object[] rowData;

    /**
     * The fields for the vehicle
     */
    private JTextField brandField, modelField, dateField, colorField, plateField,
            priceField, kmsField;

    /**
     * The status field for the vehicle
     */
    private JComboBox<Status> statusField;

    /**
     * The fuel type field for the vehicle
     */
    private JComboBox<FuelType> fuelField;

    /**
     * Constructs an edit vehicle dialog with the specified parent, row, table
     * model, and add operation
     * 
     * @param parent         the parent frame
     * @param row            the row to edit
     * @param tableModel     the table model
     * @param isAddOperation whether it is an add operation
     */
    public EditVehicleDialog(JFrame parent, int row, MyTableModel tableModel, boolean isAddOperation) {
        super(parent, "Edit Vehicle", true);

        if (isAddOperation) {
            rowData = new Object[] { "Brand", "Model", LocalDate.now().toString(), "Color", "Plate", "0",
                    Status.AVAILABLE, FuelType.DIESEL, "0", "" };
        } else
            rowData = tableModel.getDataRow(row);

        JPanel panel = new JPanel(new GridLayout(9, 2));
        brandField = new JTextField(rowData[0].toString());
        modelField = new JTextField(rowData[1].toString());
        dateField = new JTextField(rowData[2].toString());
        colorField = new JTextField(rowData[3].toString());
        plateField = new JTextField(rowData[4].toString());
        priceField = new JTextField(rowData[5].toString());
        statusField = new JComboBox<>(Status.values());
        fuelField = new JComboBox<>(FuelType.values());
        kmsField = new JTextField(rowData[8].toString());

        panel.add(new JLabel("Brand: "));
        panel.add(brandField);
        panel.add(new JLabel("Model: "));
        panel.add(modelField);
        panel.add(new JLabel("Date of Manufacture (yyyy-MM-dd): "));
        panel.add(dateField);
        panel.add(new JLabel("Color: "));
        panel.add(colorField);
        panel.add(new JLabel("License Plate: "));
        panel.add(plateField);
        panel.add(new JLabel("Price: "));
        panel.add(priceField);
        panel.add(new JLabel("Status: "));
        panel.add(statusField);
        panel.add(new JLabel("Fuel Type: "));
        panel.add(fuelField);
        panel.add(new JLabel("Kilometers: "));
        panel.add(kmsField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save changes to rowData and also checks for invalid input
                rowData[0] = brandField.getText();
                rowData[1] = modelField.getText();
                try {
                    rowData[2] = LocalDate.parse(dateField.getText());
                    rowData[2] = dateField.getText();
                } catch (DateTimeParseException ex) {
                    // Invalid input for date
                }
                rowData[3] = colorField.getText();
                rowData[4] = plateField.getText();
                try {
                    rowData[5] = String.format("%.2f", Float.parseFloat(
                            priceField.getText().replace(",", ".")));
                } catch (NumberFormatException ex) {
                    // Invalid input for price
                }
                rowData[6] = statusField.getSelectedItem();
                rowData[7] = fuelField.getSelectedItem();
                try {
                    rowData[8] = String.format("%.2f", Float.parseFloat(
                            kmsField.getText().replace(",", ".")));
                } catch (NumberFormatException ex) {
                    // Invalid input for kilometers
                }

                if (isAddOperation)
                    tableModel.addRow(rowData);
                else
                    tableModel.updateRow(row, rowData);

                // Close the dialog
                tableModel.fireTableDataChanged();
                dispose();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tableModel.deleteRow(row);
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        if (!isAddOperation)
            buttonPanel.add(deleteButton);

        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(parent);
        this.setResizable(false);
        this.setVisible(true);
    }
}
