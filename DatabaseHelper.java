package com.example.oopproject;

import java.sql.*;

public  class DatabaseHelper {
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
                "password TEXT NOT NULL," +
                "phone TEXT NOT NULL" +
                ")";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updatePasswordByPhone(String phone, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE phone = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, phone);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean validateLogin(String phone, String password) {
        String sql = "SELECT * FROM users WHERE phone = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phone);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // returns true if a matching row is found
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static void insertUser(String firstName, String lastName, String gender,
                                  String division, String district, String thana,
                                  String password , String phone) {
        String sql = "INSERT INTO users(first_name, last_name, gender, division, district, thana, password , phone) VALUES(?, ?, ?, ?, ?, ?, ? , ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, gender);
            pstmt.setString(4, division);
            pstmt.setString(5, district);
            pstmt.setString(6, thana);
            pstmt.setString(7, password);
            pstmt.setString(8, phone);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
