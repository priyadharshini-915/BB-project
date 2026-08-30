package com.bloodbank.dao;

import com.bloodbank.model.BloodStock;
import com.bloodbank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BloodStockDAO - handles all database operations for the blood_stock table.
 */
public class BloodStockDAO {

    /**
     * Adds a new blood stock entry (one per blood group expected).
     */
    public boolean addBloodStock(BloodStock stock) {
        String sql = "INSERT INTO blood_stock (blood_group, units) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stock.getBloodGroup());
            ps.setInt(2, stock.getUnits());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("addBloodStock error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns all blood stock entries.
     */
    public List<BloodStock> getAllBloodStock() {
        List<BloodStock> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_stock ORDER BY blood_group";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("getAllBloodStock error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Returns a single blood stock entry by id, or null.
     */
    public BloodStock getBloodStockById(int id) {
        String sql = "SELECT * FROM blood_stock WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.out.println("getBloodStockById error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Updates units for a blood stock entry.
     */
    public boolean updateBloodStock(BloodStock stock) {
        String sql = "UPDATE blood_stock SET blood_group = ?, units = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, stock.getBloodGroup());
            ps.setInt(2, stock.getUnits());
            ps.setInt(3, stock.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("updateBloodStock error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a blood stock entry by id.
     */
    public boolean deleteBloodStock(int id) {
        String sql = "DELETE FROM blood_stock WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("deleteBloodStock error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deducts the given number of units from a blood group's stock.
     * Only deducts if enough units are available. Returns true on success.
     */
    public boolean deductUnits(String bloodGroup, int units) {
        String checkSql = "SELECT units FROM blood_stock WHERE blood_group = ?";
        String updateSql = "UPDATE blood_stock SET units = units - ? WHERE blood_group = ? AND units >= ?";
        try (Connection conn = DBConnection.getConnection()) {
            int current = 0;
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setString(1, bloodGroup);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    current = rs.getInt("units");
                }
            }
            if (current < units) {
                return false;
            }
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, units);
                ps.setString(2, bloodGroup);
                ps.setInt(3, units);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("deductUnits error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns the total number of available blood units across all groups.
     */
    public int countTotalUnits() {
        String sql = "SELECT IFNULL(SUM(units), 0) FROM blood_stock";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countTotalUnits error: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Maps the current result set row to a BloodStock object.
     */
    private BloodStock mapRow(ResultSet rs) throws SQLException {
        BloodStock stock = new BloodStock();
        stock.setId(rs.getInt("id"));
        stock.setBloodGroup(rs.getString("blood_group"));
        stock.setUnits(rs.getInt("units"));
        stock.setUpdatedDate(rs.getString("updated_date"));
        return stock;
    }
}
