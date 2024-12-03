/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : UserModel.java
 */

package model;

import utils.DatabaseConnection;
import utils.PasswordHasher;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private final PasswordHasher passwordHasher;

    public UserModel() {
        this.passwordHasher = new PasswordHasher();
    }

    // Register a new user
    public boolean register(String name, String username, String password, String confirmPassword) throws SQLException {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }

        byte[] salt;
        String hashedPassword;
        try {
            salt = passwordHasher.getSalt();
            hashedPassword = passwordHasher.getHashedPassword(password, salt);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating password hash", e);
        }

        String saltBase64 = passwordHasher.encodeToBase64(salt);
        String query = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, saltBase64 + "." + hashedPassword);
            return stmt.executeUpdate() > 0;
        }
    }

    // Login user
    public boolean login(String username, String password) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String[] storedData = rs.getString("password").split("\\.");
                    if (storedData.length < 2) {
                        throw new IllegalArgumentException("Stored password format is incorrect.");
                    }
                    String storedSalt = storedData[0];
                    String storedHash = storedData[1];
                    byte[] salt = passwordHasher.base64Decode(storedSalt);

                    String inputHash = passwordHasher.getHashedPassword(password, salt);
                    return inputHash.equals(storedHash);
                }
            }
        }
        return false;
    }

    // Get user ID by username
    public String getUserIdByUsername(String username) throws SQLException {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id");
                }
            }
        }
        return null;
    }

    // Get username by user ID
    public String getUsernameById(String userId) throws SQLException {
        String query = "SELECT username FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        }
        return null;
    }

    // Get name by user ID
    public String getNameById(String userId) throws SQLException {
        String query = "SELECT name FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        }
        return null;
    }

    // Get name and username by user ID
    public String[] getNameAndUsernameById(String userId) throws SQLException {
        String query = "SELECT name, username FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[] { rs.getString("name"), rs.getString("username") };
                }
            }
        }
        return null;
    }
}
