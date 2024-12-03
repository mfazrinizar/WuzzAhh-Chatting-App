/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : FormValidator.java
 */

package utils;

public class FormValidator {
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && !(username.length() != 6);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6; 
    }

    public static boolean doPasswordsMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }
}