/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : ChatMessage.java
 */

package model;

import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageModel {
    // Send a message
    public boolean sendMessage(String fromUserId, String toUserId, String message) throws SQLException {
        String query = "INSERT INTO chat_messages (id_user_from, id_user_to, message) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fromUserId);
            stmt.setString(2, toUserId);
            stmt.setString(3, message);
            return stmt.executeUpdate() > 0;
        }
    }

    // Get chat messages
    public List<String[]> getChatMessages(String userId, String friendId) throws SQLException {
        String query = "SELECT id_user_from, message, sent_at FROM chat_messages " +
                "WHERE (id_user_from = ? AND id_user_to = ?) OR (id_user_from = ? AND id_user_to = ?) " +
                "ORDER BY sent_at ASC";
        List<String[]> messages = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, friendId);
            stmt.setString(3, friendId);
            stmt.setString(4, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    messages.add(new String[] {
                            rs.getString("id_user_from"),
                            rs.getString("message"),
                            rs.getTimestamp("sent_at").toString()
                    });
                }
            }
        }
        return messages;
    }

    // Get the last message
    public String getLastMessage(String userId, String friendId) throws SQLException {
        String query = "SELECT message, sent_at FROM chat_messages " +
                "WHERE (id_user_from = ? AND id_user_to = ?) OR (id_user_from = ? AND id_user_to = ?) " +
                "ORDER BY sent_at DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId);
            stmt.setString(2, friendId);
            stmt.setString(3, friendId);
            stmt.setString(4, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("message") + " (" + rs.getTimestamp("sent_at").toString() + ")";
                }
            }
        }
        return null;
    }
}
