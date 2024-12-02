/*
 * Author       : M. Fazri Nizar & Akram Ziyad Ramadhan
 * Institution  : Sriwijaya University
 * GitHub       : github.com/mfazrinizar (M. Fazri Nizar) & github.com/akam-kiko (Akram Ziyad Ramadhan)
 * File Name    : DatabaseConnection.java
 */

 package utils;

 import org.mariadb.jdbc.MariaDbPoolDataSource;
 
 import java.sql.Connection;
 import java.sql.SQLException;
 
 public class DatabaseConnection {
     private static final String DB_URL = "jdbc:mariadb://localhost:3306/wuzzahh";
     private static final String DB_USER = "root";
     private static final String DB_PASSWORD = "";
 
     private static MariaDbPoolDataSource dataSource;
 
     static {
         try {
             dataSource = new MariaDbPoolDataSource(DB_URL);
             dataSource.setUser(DB_USER);
             dataSource.setPassword(DB_PASSWORD);
         } catch (SQLException e) {
             System.err.println("Failed to initialize MariaDB connection pool: " + e.getMessage());
             e.printStackTrace();
         }
     }
 
     /**
      * Gets a connection from the MariaDB connection pool.
      *
      * @return Connection to the database
      * @throws SQLException if a connection cannot be established
      */
     public static Connection getConnection() throws SQLException {
         return dataSource.getConnection();
     }
 }
 