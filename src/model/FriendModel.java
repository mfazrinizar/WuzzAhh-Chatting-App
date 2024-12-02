/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : FriendModel.java
 */

package model;

import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendModel {
    // Add a friend
    public boolean addFriend(String userId, String friendId) throws SQLException {
        String query = "INSERT INTO friends (id_user_first, id_user_second) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, friendId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Get a list of friend IDs
    public List<String> getFriendIds(String userId) throws SQLException {
        String query = "SELECT id_user_second FROM friends WHERE id_user_first = ? " +
                "UNION SELECT id_user_first FROM friends WHERE id_user_second = ?";
        List<String> friendIds = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    friendIds.add(rs.getString("id_user_second"));
                }
            }
        }
        return friendIds;
    }

    // Check if a user is a friend
    public boolean isFriend(String userId, String friendId) throws SQLException {
        String query = "SELECT COUNT(*) FROM friends WHERE " +
                "(id_user_first = ? AND id_user_second = ?) OR " +
                "(id_user_first = ? AND id_user_second = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, friendId);
            stmt.setString(3, friendId);
            stmt.setString(4, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
