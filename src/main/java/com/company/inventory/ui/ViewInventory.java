package com.company.inventory.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;

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

/**
 * Represents the view inventory panel
 * 
 * @see JPanel
 */
public class ViewInventory extends JPanel {

    /**
     * The columns for the table
     */
    private static String[] columns = new String[] { "Brand", "Model", "Date of Manufacture",
            "Color", "License Plate", "Price", "Status", "Fuel Type", "Kilometers", "Actions" };

    /**
     * The inventory table
     */
    private JTable inventoryTable;

    /**
     * The sorter for the table
     */
    private TableRowSorter<TableModel> sorter;

    /**
     * Constructs the view inventory panel
     * 
     * @param isAdmin whether the user is an admin
     */
    public ViewInventory(boolean isAdmin) {

        // Create the table
        columns = isAdmin ? columns : Arrays.copyOfRange(columns, 0, columns.length - 1);
        inventoryTable = new JTable(new MyTableModel(columns));
        sorter = new TableRowSorter<TableModel>(inventoryTable.getModel());

        // Set the sorter for the table
        inventoryTable.setRowSorter(sorter);

        // Create the search panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        JTextField textField = new JTextField();
        searchPanel.add(new JLabel("Look for a vehicle: "), BorderLayout.WEST);
        searchPanel.add(textField, BorderLayout.CENTER);

        // Create a status bar: connected as admin or client
        JLabel statusBar = new JLabel();
        statusBar.setHorizontalAlignment(JLabel.RIGHT);
        statusBar.setFont(new Font(statusBar.getFont().getName(), Font.ITALIC, 11));

        String connectedAs = isAdmin ? "<b>admin</b>" : "<b>client</b>";
        statusBar.setText("<html>Connected as " + connectedAs + "</html>");

        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(searchPanel, BorderLayout.CENTER);
        subPanel.add(statusBar, BorderLayout.SOUTH);

        // Create the south panel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(subPanel, BorderLayout.SOUTH);

        if (isAdmin) {
            // Create a button renderer and editor for the "Actions" column
            inventoryTable.getColumnModel().getColumn(9).setCellRenderer(new ButtonRenderer());
            inventoryTable.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor(new JCheckBox()));

            // Create the add button
            JButton btnAdd = new JButton("Add");
            btnAdd.addActionListener(e -> {
                new EditVehicleDialog((JFrame) SwingUtilities.getWindowAncestor(this), -1,
                        (MyTableModel) inventoryTable.getModel(), true);
            });
            southPanel.add(btnAdd, BorderLayout.CENTER);
        }

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

        /**
         * The data
         */
        private Object[][] data;

        /**
         * The columns
         */
        private String[] columns;

        /**
         * The vehicle DAO
         */
        private VehicleDAO vehicleDAO;

        /**
         * Constructs a table model with the specified data and columns
         * 
         * @param columns the columns
         */
        public MyTableModel(String[] columns) {
            this.vehicleDAO = new VehicleDAO();
            vehicleDAO.displayDatabase();
            vehicleDAO.saveDataOnExit();
            this.data = vehicleDAO.getVehicles();
            this.columns = columns;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        /**
         * Returns the data row at the specified row
         * 
         * @param row the row
         * @return the data row
         */
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

            // Add vehicle to database
            vehicleDAO.updateVehicle(-1, rowData);
            data = vehicleDAO.getVehicles();
            vehicleDAO.displayDatabase();

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

                // Update vehicle in database
                vehicleDAO.updateVehicle(row, rowData);
                data = vehicleDAO.getVehicles();
                vehicleDAO.displayDatabase();

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

                // Update the data
                vehicleDAO.removeVehicle(data[row][4].toString());
                data = vehicleDAO.getVehicles();
                vehicleDAO.displayDatabase();

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
        private JButton button;
        private int selectedRow;

        /**
         * Constructs a button editor with the specified checkbox
         * 
         * @param checkBox the checkbox
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
