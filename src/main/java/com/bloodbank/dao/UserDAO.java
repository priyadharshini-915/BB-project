package com.bloodbank.dao;

import com.bloodbank.model.User;
import com.bloodbank.util.DBConnection;
import com.bloodbank.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO - handles all database operations for the users table.
 */
public class UserDAO {

    /**
     * Registers a new user. Password is hashed before storing.
     * Returns true on success, false if failed (e.g. duplicate email).
     */
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (full_name, email, phone, password, blood_group, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, PasswordUtil.hashPassword(user.getPassword()));
            ps.setString(5, user.getBloodGroup());
            ps.setString(6, user.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("registerUser error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Authenticates a user by email and password.
     * Returns the User object if credentials match, otherwise null.
     */
    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        String hashed = PasswordUtil.hashPassword(password);
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, hashed);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setBloodGroup(rs.getString("blood_group"));
                user.setAddress(rs.getString("address"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("loginUser error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns all registered users.
     */
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setBloodGroup(rs.getString("blood_group"));
                user.setAddress(rs.getString("address"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("getAllUsers error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Deletes a user by id.
     */
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("deleteUser error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns the total number of registered users.
     */
    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM users";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countUsers error: " + e.getMessage());
        }
        return 0;
    }
}
