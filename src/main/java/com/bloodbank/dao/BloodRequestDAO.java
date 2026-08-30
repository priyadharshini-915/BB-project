package com.bloodbank.dao;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * BloodRequestDAO - handles all database operations for the blood_requests table.
 */
public class BloodRequestDAO {

    /**
     * Submits a new blood request (default status = Pending).
     */
    public boolean submitRequest(BloodRequest request) {
        String sql = "INSERT INTO blood_requests "
                + "(patient_name, blood_group, required_units, hospital_name, contact_number, "
                + "request_date, address, reason, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Pending')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, request.getPatientName());
            ps.setString(2, request.getBloodGroup());
            ps.setInt(3, request.getRequiredUnits());
            ps.setString(4, request.getHospitalName());
            ps.setString(5, request.getContactNumber());
            ps.setString(6, request.getRequestDate());
            ps.setString(7, request.getAddress());
            ps.setString(8, request.getReason());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("submitRequest error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns all blood requests.
     */
    public List<BloodRequest> getAllRequests() {
        List<BloodRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM blood_requests ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("getAllRequests error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Updates the status of a request (Approve/Reject).
     */
    public boolean updateRequestStatus(int id, String status) {
        String sql = "UPDATE blood_requests SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("updateRequestStatus error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a request by id.
     */
    public boolean deleteRequest(int id) {
        String sql = "DELETE FROM blood_requests WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("deleteRequest error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Counts requests with a specific status (e.g. Pending, Approved).
     */
    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM blood_requests WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countByStatus error: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Maps the current result set row to a BloodRequest object.
     */
    private BloodRequest mapRow(ResultSet rs) throws SQLException {
        BloodRequest req = new BloodRequest();
        req.setId(rs.getInt("id"));
        req.setPatientName(rs.getString("patient_name"));
        req.setBloodGroup(rs.getString("blood_group"));
        req.setRequiredUnits(rs.getInt("required_units"));
        req.setHospitalName(rs.getString("hospital_name"));
        req.setContactNumber(rs.getString("contact_number"));
        req.setRequestDate(rs.getString("request_date"));
        req.setAddress(rs.getString("address"));
        req.setReason(rs.getString("reason"));
        req.setStatus(rs.getString("status"));
        return req;
    }
}
