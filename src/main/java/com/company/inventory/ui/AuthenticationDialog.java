package com.company.inventory.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Represents the authentication form
 * 
 * @see JFrame
 */
public class AuthenticationDialog extends JDialog {

    /**
     * The username
     */
    private String username;

    /**
     * The password
     */
    private String password;

    /**
     * Whether the user is an admin
     */
    private boolean isAdmin;

    /**
     * Whether an action has been performed
     */
    public boolean actionPerformed;

    /**
     * Constructs an authentication form
     * 
     * @param parent the parent frame
     */
    public AuthenticationDialog(JFrame parent) {
        super(parent, "Authentication", true);
        this.setTitle("Authentication Form");
        this.setSize(500, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(null); // Center the window

        actionPerformed = false;

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                password = new String(passwordChars);

                // Perform authentication logic here
                // For now, username and password are admin
                isAdmin = username.equals("admin") && password.equals("admin");

                // Try again
                if (!isAdmin) {
                    usernameField.setText("Invalid username or password");
                    passwordField.setText("");
                } else {
                    actionPerformed = true;
                    dispose();
                }

            }
        });

        JButton clientButton = new JButton("Client");
        clientButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isAdmin = false;
                actionPerformed = true;
                dispose();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(clientButton);

        this.add(panel);
        this.setVisible(true);
    }

    /**
     * Gets the isAdmin flag
     * 
     * @return isAdmin
     */
    public boolean isAdmin() {
        return isAdmin;
    }
}