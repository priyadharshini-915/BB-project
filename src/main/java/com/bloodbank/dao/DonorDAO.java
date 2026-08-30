package com.bloodbank.dao;

import com.bloodbank.model.Donor;
import com.bloodbank.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DonorDAO - handles all database operations for the donors table.
 */
public class DonorDAO {

    /**
     * Adds a new donor to the database.
     */
    public boolean addDonor(Donor donor) {
        String sql = "INSERT INTO donors (donor_name, age, gender, blood_group, phone, email, address, last_donation_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, donor.getDonorName());
            ps.setInt(2, donor.getAge());
            ps.setString(3, donor.getGender());
            ps.setString(4, donor.getBloodGroup());
            ps.setString(5, donor.getPhone());
            ps.setString(6, donor.getEmail());
            ps.setString(7, donor.getAddress());
            ps.setString(8, donor.getLastDonationDate());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("addDonor error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns all donors.
     */
    public List<Donor> getAllDonors() {
        List<Donor> list = new ArrayList<>();
        String sql = "SELECT * FROM donors ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("getAllDonors error: " + e.getMessage());
        }
        return list;
    }

    /**
     * Returns a single donor by id, or null if not found.
     */
    public Donor getDonorById(int id) {
        String sql = "SELECT * FROM donors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            System.out.println("getDonorById error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Updates an existing donor.
     */
    public boolean updateDonor(Donor donor) {
        String sql = "UPDATE donors SET donor_name = ?, age = ?, gender = ?, blood_group = ?, "
                + "phone = ?, email = ?, address = ?, last_donation_date = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, donor.getDonorName());
            ps.setInt(2, donor.getAge());
            ps.setString(3, donor.getGender());
            ps.setString(4, donor.getBloodGroup());
            ps.setString(5, donor.getPhone());
            ps.setString(6, donor.getEmail());
            ps.setString(7, donor.getAddress());
            ps.setString(8, donor.getLastDonationDate());
            ps.setInt(9, donor.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("updateDonor error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a donor by id.
     */
    public boolean deleteDonor(int id) {
        String sql = "DELETE FROM donors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("deleteDonor error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns the total number of donors.
     */
    public int countDonors() {
        String sql = "SELECT COUNT(*) FROM donors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countDonors error: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Maps the current result set row to a Donor object.
     */
    private Donor mapRow(ResultSet rs) throws SQLException {
        Donor donor = new Donor();
        donor.setId(rs.getInt("id"));
        donor.setDonorName(rs.getString("donor_name"));
        donor.setAge(rs.getInt("age"));
        donor.setGender(rs.getString("gender"));
        donor.setBloodGroup(rs.getString("blood_group"));
        donor.setPhone(rs.getString("phone"));
        donor.setEmail(rs.getString("email"));
        donor.setAddress(rs.getString("address"));
        donor.setLastDonationDate(rs.getString("last_donation_date"));
        return donor;
    }
}
