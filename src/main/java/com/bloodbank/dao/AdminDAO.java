package com.bloodbank.dao;

import com.bloodbank.model.Admin;
import com.bloodbank.util.DBConnection;
import com.bloodbank.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AdminDAO - handles database operations for the admin table.
 */
public class AdminDAO {

    /**
     * Authenticates an admin by username and password.
     * Returns the Admin object if credentials match, otherwise null.
     */
    public Admin loginAdmin(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        String hashed = PasswordUtil.hashPassword(password);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashed);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                return admin;
            }
        } catch (SQLException e) {
            System.out.println("loginAdmin error: " + e.getMessage());
        }
        return null;
    }
}
