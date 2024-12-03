/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : RegisterView.java
 */

 package view;

 import controller.FormController;
 
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.sql.SQLException;
 
 public class RegisterView extends JFrame {
     private JPanel headerPanel, formPanel;
     private JLabel headerLabel;
     private JLabel nameLabel, usernameLabel, passwordLabel, confirmPasswordLabel;
     private JTextField nameField, usernameField;
     private JPasswordField passwordField, confirmPasswordField;
     private JButton registerButton, backButton;
     private FormController formController;
 
     public RegisterView() {
         // Initialize FormController
         formController = new FormController();
 
         // Frame configuration
         setTitle("Register Form");
         setSize(400, 500);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLayout(new BorderLayout());
 
         // Header panel
         headerPanel = new JPanel();
         headerPanel.setBackground(new Color(102, 102, 102));
         headerLabel = new JLabel("REGISTER NOW");
         headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
         headerLabel.setForeground(Color.WHITE);
         headerPanel.add(headerLabel);
 
         // Form panel with GridBagLayout
         formPanel = new JPanel(new GridBagLayout());
         formPanel.setBackground(new Color(240, 240, 240));
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(10, 10, 10, 10); // Padding between components
 
         // Labels and fields
         nameLabel = new JLabel("Name:");
         usernameLabel = new JLabel("Username:");
         passwordLabel = new JLabel("Password:");
         confirmPasswordLabel = new JLabel("Confirm Password:");
 
         nameField = new JTextField(15);
         usernameField = new JTextField(15);
         passwordField = new JPasswordField(15);
         confirmPasswordField = new JPasswordField(15);
 
         // Add components to the form panel
         gbc.anchor = GridBagConstraints.WEST;
         gbc.gridx = 0;
         gbc.gridy = 0;
         formPanel.add(nameLabel, gbc);
         gbc.gridx = 1;
         formPanel.add(nameField, gbc);
 
         gbc.gridx = 0;
         gbc.gridy = 1;
         formPanel.add(usernameLabel, gbc);
         gbc.gridx = 1;
         formPanel.add(usernameField, gbc);
 
         gbc.gridx = 0;
         gbc.gridy = 2;
         formPanel.add(passwordLabel, gbc);
         gbc.gridx = 1;
         formPanel.add(passwordField, gbc);
 
         gbc.gridx = 0;
         gbc.gridy = 3;
         formPanel.add(confirmPasswordLabel, gbc);
         gbc.gridx = 1;
         formPanel.add(confirmPasswordField, gbc);
 
         // Buttons panel
         JPanel buttonPanel = new JPanel();
         registerButton = new JButton("Register");
         backButton = new JButton("Back");
         buttonPanel.add(backButton);
         buttonPanel.add(registerButton);
 
         // Add panels to the frame
         add(headerPanel, BorderLayout.NORTH);
         add(formPanel, BorderLayout.CENTER);
         add(buttonPanel, BorderLayout.SOUTH);
 
         // Button actions
         registerButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 // Handle registration logic here
                 String name = nameField.getText();
                 String username = usernameField.getText();
                 String password = new String(passwordField.getPassword());
                 String confirmPassword = new String(confirmPasswordField.getPassword());
 
                 try {
                     if (formController.registerUser(name, username, password, confirmPassword)) {
                         JOptionPane.showMessageDialog(null, "Registered Successfully!");
                     } else {
                         JOptionPane.showMessageDialog(null, "Registration Failed!");
                     }
                 } catch (SQLException ex) {
                     JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                 } catch (IllegalArgumentException ex) {
                     JOptionPane.showMessageDialog(null, ex.getMessage());
                 }
             }
         });
 
         backButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 dispose(); // Close the current frame
             }
         });
 
         setVisible(true);
     }
 }

