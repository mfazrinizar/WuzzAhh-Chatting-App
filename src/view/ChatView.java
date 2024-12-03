/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : ChatView.java
 */

 package view;

 import model.ChatMessageModel;
 
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.ActionEvent;
 import java.awt.event.ActionListener;
 import java.sql.SQLException;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.List;
 
 public class ChatView extends JFrame {
     private String userId;
     private String friendUserId;
     private String friendUserName;
     private String friendName;
     private ChatMessageModel chatMessageModel;
     private JPanel chatPanel;
 
     public ChatView(String userId, String friendUserId, String friendUserName, String friendName) {
         this.userId = userId;
         this.friendUserId = friendUserId;
         this.friendUserName = friendUserName;
         this.friendName = friendName;
         chatMessageModel = new ChatMessageModel();
 
         setTitle("Chat with " + friendName);
         setSize(400, 600);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLayout(new BorderLayout());
 
         initComponents();
         loadChatMessages();
         setVisible(true);
     }
 
     private void initComponents() {
         // Header panel with profile picture, name, and back button
         JPanel headerPanel = new JPanel();
         headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
         headerPanel.setBackground(new Color(37, 211, 102));
 
         // Profile picture
         JLabel profileLabel = new JLabel(String.valueOf(friendName.charAt(0)), SwingConstants.CENTER);
         profileLabel.setOpaque(true);
         profileLabel.setBackground(new Color(200, 200, 200));
         profileLabel.setPreferredSize(new Dimension(40, 40));
         profileLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
         profileLabel.setForeground(Color.WHITE);
         profileLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
         headerPanel.add(profileLabel);
 
         // Name
         JLabel nameLabel = new JLabel(friendName + " (" + friendUserName + ")");
         nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
         nameLabel.setForeground(Color.WHITE);
         headerPanel.add(nameLabel);
 
         // Back button
         JButton backButton = new JButton("Back");
         backButton.setBackground(new Color(37, 211, 102));
         backButton.setForeground(Color.WHITE);
         backButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                DashboardView dashboardView = new DashboardView(userId);
                dashboardView.setVisible(true);
                dispose();
             }
         });
         headerPanel.add(backButton);
 
         add(headerPanel, BorderLayout.NORTH);
 
         // Panel to display chat messages
         chatPanel = new JPanel();
         chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
         JScrollPane scrollPane = new JScrollPane(chatPanel);
         add(scrollPane, BorderLayout.CENTER);
 
         // Panel to type messages
         JPanel inputPanel = new JPanel();
         inputPanel.setLayout(new BorderLayout());
 
         JTextArea messageArea = new JTextArea(3, 20);
         messageArea.setLineWrap(true);
         JScrollPane messageScroll = new JScrollPane(messageArea);
         inputPanel.add(messageScroll, BorderLayout.CENTER);
 
         // Send button
         JButton sendButton = new JButton("Send");
         sendButton.setBackground(new Color(37, 211, 102)); 
         sendButton.setForeground(Color.WHITE);
         sendButton.setPreferredSize(new Dimension(80, 30));
         sendButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 String message = messageArea.getText().trim();
                 if (!message.isEmpty()) {
                     try {
                         if (chatMessageModel.sendMessage(userId, friendUserId, message)) {
                             addMessageToChatPanel(userId, message, new SimpleDateFormat("HH:mm").format(new Date()));
                             messageArea.setText(""); 
                         } else {
                             JOptionPane.showMessageDialog(ChatView.this, "Failed to send message.");
                         }
                     } catch (SQLException ex) {
                         JOptionPane.showMessageDialog(ChatView.this, "Error: " + ex.getMessage());
                     }
                 }
             }
         });
         inputPanel.add(sendButton, BorderLayout.EAST);
         add(inputPanel, BorderLayout.SOUTH);
     }
 
     private void loadChatMessages() {
         try {
             List<String[]> messages = chatMessageModel.getChatMessages(userId, friendUserId);
             for (String[] message : messages) {
                 addMessageToChatPanel(message[0], message[1], message[2]);
             }
         } catch (SQLException e) {
             JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
         }
     }
 
     private void addMessageToChatPanel(String senderId, String message, String timestamp) {
         JPanel messagePanel = new JPanel();
         messagePanel.setLayout(new FlowLayout(senderId.equals(userId) ? FlowLayout.LEFT : FlowLayout.RIGHT));
         messagePanel.setMaximumSize(new Dimension(400, 50));
 
         JLabel messageLabel = new JLabel("<html><p style=\"background-color:" + (senderId.equals(userId) ? "#DCF8C6" : "#FFFFFF") + "; padding:10px; border-radius:10px;\">" + message + "<br><span style='font-size:10px; color:gray;'>" + timestamp + "</span></p></html>");
         messagePanel.add(messageLabel);
         chatPanel.add(messagePanel);
 
         chatPanel.revalidate();
         chatPanel.repaint();
     }
 }
