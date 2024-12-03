/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : ChatController.java
 */

package controller;

import model.ChatMessageModel;

import java.sql.SQLException;
import java.util.List;

public class ChatController {
    private ChatMessageModel chatMessageModel;

    public ChatController() {
        chatMessageModel = new ChatMessageModel();
    }

    public boolean sendMessage(String fromUserId, String toUserId, String message) throws SQLException {
        return chatMessageModel.sendMessage(fromUserId, toUserId, message);
    }

    public List<String[]> getChatMessages(String userId, String friendId) throws SQLException {
        return chatMessageModel.getChatMessages(userId, friendId);
    }
}