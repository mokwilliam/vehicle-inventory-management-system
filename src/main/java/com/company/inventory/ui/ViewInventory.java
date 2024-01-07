package com.company.inventory.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.time.LocalDate;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.company.inventory.dao.VehicleDAO;
import com.company.inventory.model.VehicleModel;
import com.company.inventory.model.VehicleModel.FuelType;
import com.company.inventory.model.VehicleModel.Status;

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

    // Create the "Add" button
    private JButton btnAdd;

    // Default data for the table
    protected Object[][] data = new Object[][] {
            { "Toyota", "Camry", "2019-01-01", "White", "ABC123", 59999.99, "AVAILABLE", "HYBRID", 168712, "" },
            { "BMW", "M3", "2019-01-01", "Black", "DEF456", 100000, "AVAILABLE", "PETROL", 98432, "" }
    };

    // Create the table
    protected JTable inventoryTable = new JTable(new MyTableModel(data, columns));
    protected TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(inventoryTable.getModel());
    protected JTextField textField = new JTextField();

    /**
     * Constructs the view inventory panel
     */
    public ViewInventory() {
        // Set the sorter for the table
        inventoryTable.setRowSorter(sorter);

        // Create a button renderer and editor for the "Actions" column
        inventoryTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
        inventoryTable.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Add ActionListeners to the buttons
        btnAdd = new JButton("Add");
        btnAdd.addActionListener(e -> {
            new EditVehicleDialog((JFrame) SwingUtilities.getWindowAncestor(this), -1,
                    (MyTableModel) inventoryTable.getModel(), true);
        });

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

    /**
     * Represents the table model for the inventory table
     * 
     * @see AbstractTableModel
     */
    public class MyTableModel extends AbstractTableModel {

        private Object[][] data;
        private String[] columns;
        private VehicleDAO vehicleDAO;

        /**
         * Constructs a table model with the specified data and columns
         */
        public MyTableModel(Object[][] data, String[] columns) {
            this.data = data;
            this.columns = columns;
            this.vehicleDAO = new VehicleDAO();
        }

        /**
         * Formats the data from the table to a VehicleModel
         * 
         * @param rowData the data from the table
         * @return the VehicleModel
         */
        public VehicleModel formatVehicleModel(Object[] rowData) {
            return new VehicleModel(
                    rowData[0].toString(),
                    rowData[1].toString(),
                    LocalDate.parse(rowData[2].toString()),
                    rowData[3].toString(),
                    rowData[4].toString(),
                    Float.valueOf(rowData[5].toString()),
                    Status.valueOf(rowData[6].toString()),
                    FuelType.valueOf(rowData[7].toString()),
                    Float.valueOf(rowData[8].toString()));
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        public Object[] getDataRow(int row) {
            return data[row];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columns[column]; // Return the specific column name from the 'columns' array
        }

        /**
         * Adds a row to the table
         * 
         * @param rowData the data to add
         */
        public void addRow(Object[] rowData) {
            Object[][] newData = new Object[data.length + 1][columns.length];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            newData[data.length] = rowData;
            data = newData;

            // Add vehicle to database
            vehicleDAO.addVehicle(formatVehicleModel(rowData));

            // Notify the table of changes
            fireTableRowsInserted(data.length - 1, data.length - 1);
        }

        /**
         * Updates a row in the table
         * 
         * @param row     the row to update
         * @param rowData the data to update
         */
        public void updateRow(int row, Object[] rowData) {
            if (row >= 0 && row < data.length) {
                data[row] = rowData;

                // Update vehicle in database
                vehicleDAO.updateVehicle(formatVehicleModel(rowData));

                // Notify the table of changes
                fireTableRowsUpdated(row, row);
            }
        }

        /**
         * Deletes a row from the table
         * 
         * @param row the row to delete
         */
        public void deleteRow(int row) {
            if (row >= 0 && row < data.length) {
                Object[][] newData = new Object[data.length - 1][columns.length];
                int idx = 0;
                for (int i = 0; i < data.length; i++) {
                    if (i != row) {
                        newData[idx++] = data[i];
                    }
                }
                data = newData;

                // Remove vehicle from database
                vehicleDAO.removeVehicle(formatVehicleModel(data[row]).licensePlate);

                // Notify the table of changes
                fireTableRowsDeleted(row, row);
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 9; // Allow editing only for the "Actions" column
        }

    }

    /**
     * Represents a button renderer for the "Actions" column
     */
    class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructs a button renderer
         */
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            setText("Modify/Remove");
            return this;
        }
    }

    /**
     * Represents a button editor for the "Actions" column
     */
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private int selectedRow;

        /**
         * Constructs a button editor with the specified checkbox
         */
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setText("Modify/Remove");
            // Open the EditVehicleDialog when the button is clicked
            button.addActionListener(e -> {
                new EditVehicleDialog((JFrame) SwingUtilities.getWindowAncestor(button.getParent()),
                        selectedRow, (MyTableModel) ((JTable) button.getParent()).getModel(), false);
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                int row, int column) {
            selectedRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public boolean stopCellEditing() {
            return super.stopCellEditing();
        }
    }

}
