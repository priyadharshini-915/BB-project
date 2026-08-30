package com.bloodbank.servlet;

import com.bloodbank.dao.DonorDAO;
import com.bloodbank.model.Donor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * AdminDonorServlet - CRUD operations for donors from the admin panel.
 * Action is supplied via the "action" parameter: add, update, delete, edit, list.
 */
@WebServlet("/admin-manage-donors")
public class AdminDonorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DonorDAO donorDAO = new DonorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Listing and edit-load always use GET
        String action = request.getParameter("action");
        List<Donor> donors = donorDAO.getAllDonors();
        request.setAttribute("donors", donors);

        if ("delete".equals(action)) {
            handleDelete(request);
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("editingDonor", donorDAO.getDonorById(id));
        }

        request.getRequestDispatcher("admin-manage-donors.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Adding and updating use POST
        String action = request.getParameter("action");

        Donor donor = new Donor();
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            donor.setId(Integer.parseInt(request.getParameter("id")));
        }
        donor.setDonorName(request.getParameter("donorName"));
        donor.setAge(Integer.parseInt(request.getParameter("age")));
        donor.setGender(request.getParameter("gender"));
        donor.setBloodGroup(request.getParameter("bloodGroup"));
        donor.setPhone(request.getParameter("phone"));
        donor.setEmail(request.getParameter("email"));
        donor.setAddress(request.getParameter("address"));
        donor.setLastDonationDate(request.getParameter("lastDonationDate"));

        boolean success;
        if ("update".equals(action)) {
            success = donorDAO.updateDonor(donor);
        } else {
            success = donorDAO.addDonor(donor);
        }

        request.setAttribute("message", success ? "Donor saved successfully." : "Operation failed.");
        request.setAttribute("alertType", success ? "success" : "danger");
        request.setAttribute("donors", donorDAO.getAllDonors());
        request.getRequestDispatcher("admin-manage-donors.jsp").forward(request, response);
    }

    private void handleDelete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        if (donorDAO.deleteDonor(id)) {
            request.setAttribute("message", "Donor deleted successfully.");
            request.setAttribute("alertType", "success");
        } else {
            request.setAttribute("message", "Failed to delete donor.");
            request.setAttribute("alertType", "danger");
        }
    }
}
