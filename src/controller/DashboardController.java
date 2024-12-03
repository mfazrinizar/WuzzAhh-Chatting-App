/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : DashboardController.java
 */

package controller;

import model.FriendModel;
import model.ChatMessageModel;
import model.UserModel;

import java.sql.SQLException;
import java.util.List;

public class DashboardController {
    private FriendModel friendModel;
    private ChatMessageModel chatMessageModel;
    private UserModel userModel;

    public DashboardController() {
        friendModel = new FriendModel();
        chatMessageModel = new ChatMessageModel();
        userModel = new UserModel();
    }

    public List<String> getFriendIds(String userId) throws SQLException {
        return friendModel.getFriendIds(userId);
    }

    public String[] getNameAndUsernameById(String userId) throws SQLException {
        return userModel.getNameAndUsernameById(userId);
    }

    public String getUserIdByUsername(String username) throws SQLException {
        return userModel.getUserIdByUsername(username);
    }

    public boolean addFriend(String userId, String friendId) throws SQLException {
        return friendModel.addFriend(userId, friendId);
    }

    public String getLastMessage(String userId, String friendId) throws SQLException {
        return chatMessageModel.getLastMessage(userId, friendId);
    }
}