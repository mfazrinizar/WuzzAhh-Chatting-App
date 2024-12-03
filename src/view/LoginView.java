/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : LoginView.java
 */

package view;

import controller.FormController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginView extends JFrame {
    private JPanel headerPanel, formPanel;
    private JLabel headerLabel, welcomeLabel, instructionLabel;
    private JLabel usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private FormController formController;

    public LoginView() {
        // Initialize FormController
        formController = new FormController();

        // Frame configuration
        setTitle("Login Form");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        // Header panel
        headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 70, 70));
        headerPanel.setPreferredSize(new Dimension(500, 60));

        headerLabel = new JLabel("LOGIN NOW");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Form panel
        formPanel = new JPanel();
        formPanel.setBackground(new Color(102, 153, 255));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome label
        welcomeLabel = new JLabel("WELCOME", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(welcomeLabel, gbc);

        // Instruction label
        instructionLabel = new JLabel("Insert your username and password!", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        formPanel.add(instructionLabel, gbc);

        // Username label and field
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        usernameLabel.setForeground(Color.WHITE);
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(usernameField, gbc);

        // Password label and field
        gbc.gridy = 3;
        gbc.gridx = 0;
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordLabel.setForeground(Color.WHITE);
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(passwordField, gbc);

        // Login button
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        loginButton = createStyledButton("Login");
        formPanel.add(loginButton, gbc);

        // Register button
        gbc.gridy = 5;
        registerButton = createStyledButton("Register");
        formPanel.add(registerButton, gbc);

        // Add panels to the frame
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Button actions
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    if (formController.loginUser(username, password)) {
                        String userId = formController.getUserIdByUsername(username);
                        JOptionPane.showMessageDialog(null, "Login Successful!");
                        DashboardView dashboardView = new DashboardView(userId);
                        dashboardView.setVisible(true);
                        dispose();
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterView();
                dispose();
            }
        });

        setVisible(true);
    }

    // Method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
