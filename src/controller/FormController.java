/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : FormController.java
 */

package controller;

import model.UserModel;
import utils.FormValidator;

import java.sql.SQLException;

public class FormController {
    private UserModel userModel;

    public FormController() {
        this.userModel = new UserModel();
    }

    public boolean registerUser(String name, String username, String password, String confirmPassword)
            throws SQLException {
        if (!FormValidator.isValidName(name)) {
            throw new IllegalArgumentException("Invalid name (mustn't be empty).");
        }
        if (!FormValidator.isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username (must be 6 characters).");
        }
        if (!FormValidator.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password (at least 6 characters).");
        }
        if (!FormValidator.doPasswordsMatch(password, confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        return userModel.register(name, username, password, confirmPassword);
    }

    public boolean loginUser(String username, String password) throws SQLException {
        if (!FormValidator.isValidUsername(username)) {
            throw new IllegalArgumentException("Invalid username (must be 6 characters).");
        }
        if (!FormValidator.isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password (at least 6 characters).");
        }
        return userModel.login(username, password);
    }
}
