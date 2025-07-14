package org.example.java;

import java.sql.*;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:user_data.db";

    public static void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "gender TEXT NOT NULL," +
                "division TEXT NOT NULL," +
                "district TEXT NOT NULL," +
                "thana TEXT NOT NULL," +
                "password TEXT NOT NULL)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(String firstName, String lastName, String gender,
                                  String division, String district, String thana,
                                  String password) {
        String sql = "INSERT INTO users(first_name, last_name, gender, division, district, thana, password) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setString(4, division);
            pstmt.setString(5, district);
            pstmt.setString(6, thana);
            pstmt.setString(7, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
