/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : DashboardView.java
 */

 package view;

 import model.FriendModel;
 import model.ChatMessageModel;
 import model.UserModel;
 
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.awt.event.MouseAdapter;
 import java.awt.event.MouseEvent;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.List;
 
 public class DashboardView extends JFrame {
     private JPanel contactPanel;
     private ArrayList<String> contacts;
     private FriendModel friendModel;
     private ChatMessageModel chatMessageModel;
     private UserModel userModel;
     private String userId;
 
     public DashboardView(String userId) {
         this.userId = userId;
         friendModel = new FriendModel();
         chatMessageModel = new ChatMessageModel();
         userModel = new UserModel();
 
         setTitle("WuzzAhh");
         setSize(450, 600);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
 
         contacts = new ArrayList<>();
         initComponents();
         loadFriends();
     }
 
     private void initComponents() {
         // Header panel
         JPanel headerPanel = new JPanel(new BorderLayout());
         JLabel appLabel = new JLabel("WuzzAhh");
         appLabel.setFont(new Font("Tw Cen MT", Font.BOLD, 28));
         appLabel.setForeground(Color.WHITE);
 
         JButton logoutButton = new JButton("←");
         logoutButton.setForeground(Color.WHITE);
         logoutButton.setBackground(new Color(76, 175, 80));
         logoutButton.setFocusPainted(false);
         logoutButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 new LoginView();
                 dispose();
             }
         });
 
         // Header styling
         headerPanel.setBackground(new Color(76, 175, 80));
         headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
         headerPanel.add(appLabel, BorderLayout.WEST);
         headerPanel.add(logoutButton, BorderLayout.EAST);
 
         // Contact list panel
         contactPanel = new JPanel(new GridBagLayout());
         contactPanel.setBackground(Color.WHITE);
 
         // Scroll pane for contacts
         JScrollPane scrollPane = new JScrollPane(contactPanel);
         scrollPane.setBorder(null);
         scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
         scrollPane.setPreferredSize(new Dimension(450, 450));
 
         // Add friend button
         JButton addFriendButton = new JButton("+");
         addFriendButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
         addFriendButton.setBackground(new Color(76, 175, 80));
         addFriendButton.setForeground(Color.WHITE);
         addFriendButton.setFocusPainted(false);
         addFriendButton.setBorderPainted(false);
         addFriendButton.setPreferredSize(new Dimension(450, 50));
         addFriendButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
                 addFriendAction();
             }
         });
 
         // Main layout
         setLayout(new BorderLayout());
         add(headerPanel, BorderLayout.NORTH);
         add(scrollPane, BorderLayout.CENTER);
         add(addFriendButton, BorderLayout.SOUTH);
     }
 
     private JPanel createChatPanel(String name, String message, String time, String friendUserId, String friendUserName, String friendName) {
         JPanel panel = new JPanel(new BorderLayout());
         panel.setBorder(BorderFactory.createCompoundBorder(
                 BorderFactory.createEmptyBorder(3, 8, 3, 8),
                 BorderFactory.createLineBorder(new Color(220, 220, 220))));
         panel.setBackground(Color.WHITE);
         panel.setPreferredSize(new Dimension(400, 60));
 
         // Avatar placeholder
         JLabel avatar = new JLabel(String.valueOf(name.charAt(0)), SwingConstants.CENTER);
         avatar.setOpaque(true);
         avatar.setBackground(new Color(200, 200, 200));
         avatar.setPreferredSize(new Dimension(40, 40));
         avatar.setFont(new Font("Segoe UI", Font.BOLD, 20));
         avatar.setForeground(Color.WHITE);
         panel.add(avatar, BorderLayout.WEST);
 
         // Name and message panel
         JPanel textPanel = new JPanel(new BorderLayout());
         textPanel.setBackground(Color.WHITE);
         JLabel nameLabel = new JLabel(name);
         nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
 
         JLabel messageLabel = new JLabel("<html><p style='width:250px'>" + message + "</p></html>");
         messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
 
         textPanel.add(nameLabel, BorderLayout.NORTH);
         textPanel.add(messageLabel, BorderLayout.CENTER);
 
         // Time label
         JLabel timeLabel = new JLabel(time);
         timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
         timeLabel.setForeground(Color.GRAY);
         timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
 
         // Time in the top-right corner
         panel.add(timeLabel, BorderLayout.EAST);
         panel.add(textPanel, BorderLayout.CENTER);
 
         // Add mouse listener to navigate to ChatView
         panel.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 new ChatView(userId, friendUserId, friendUserName, friendName);
                 dispose();
             }
         });
 
         return panel;
     }
 
     private void addFriendAction() {
         String friendUsername = JOptionPane.showInputDialog(this, "Enter friend's username:");
         if (friendUsername != null && !friendUsername.trim().isEmpty()) {
             try {
                 String friendId = userModel.getUserIdByUsername(friendUsername);
                 if (friendId != null && !friendId.isEmpty() && !friendId.equals(userId)) {
                     if (friendModel.addFriend(userId, friendId)) {
                         contacts.add(friendUsername);
                         updateContactList();
                     } else {
                         JOptionPane.showMessageDialog(this, "Failed to add friend.");
                     }
                 } else {
                     JOptionPane.showMessageDialog(this, "User not found.");
                 }
             } catch (SQLException e) {
                 JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
             }
         } else {
             JOptionPane.showMessageDialog(this, "Username cannot be empty!");
         }
     }
 
     private void updateContactList() {
         contactPanel.removeAll();
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(5, 0, 5, 0); 
         gbc.gridx = 0;
         gbc.gridy = GridBagConstraints.RELATIVE;
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gbc.anchor = GridBagConstraints.NORTH; 
         gbc.weightx = 1.0;
 
         for (String contact : contacts) {
             try {
                 String userName = contact.substring(contact.indexOf("(") + 1, contact.indexOf(")"));
                 String friendId = userModel.getUserIdByUsername(userName);
                 String lastMessage = chatMessageModel.getLastMessage(userId, friendId);
                 String[] friendDetails = userModel.getNameAndUsernameById(friendId);
                 JPanel panel = createChatPanel(contact, lastMessage != null ? lastMessage : "No messages yet",
                         "-:- PM", friendId, friendDetails[1], friendDetails[0]);
                 contactPanel.add(panel, gbc);
             } catch (SQLException e) {
                 JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
             }
         }
 
         // Add a filler component to push items to the top
         gbc.weighty = 1.0;
         contactPanel.add(Box.createVerticalGlue(), gbc);
 
         contactPanel.revalidate();
         contactPanel.repaint();
     }
 
     private void loadFriends() {
         try {
             List<String> friendIds = friendModel.getFriendIds(userId);
             for (String friendId : friendIds) {
                 String[] friendUsername = userModel.getNameAndUsernameById(friendId);
                 if (friendUsername != null) {
                     contacts.add(friendUsername[0] + " (" + friendUsername[1] + ")");
                 }
             }
             updateContactList();
         } catch (SQLException e) {
             JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
         }
     }
 }