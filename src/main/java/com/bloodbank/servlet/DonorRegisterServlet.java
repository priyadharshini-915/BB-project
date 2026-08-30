package com.bloodbank.servlet;

import com.bloodbank.dao.DonorDAO;
import com.bloodbank.model.Donor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * DonorRegisterServlet - handles donor registration by users.
 */
@WebServlet("/DonorRegisterServlet")
public class DonorRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DonorDAO donorDAO = new DonorDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String donorName = request.getParameter("donorName");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String bloodGroup = request.getParameter("bloodGroup");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String lastDonationDate = request.getParameter("lastDonationDate");

        if (isEmpty(donorName) || isEmpty(age) || isEmpty(gender) || isEmpty(bloodGroup)
                || isEmpty(phone) || isEmpty(address)) {
            redirectWithMessage(response, "donor.html", "All required fields must be filled.", "danger");
            return;
        }

        Donor donor = new Donor();
        donor.setDonorName(donorName);
        donor.setAge(Integer.parseInt(age));
        donor.setGender(gender);
        donor.setBloodGroup(bloodGroup);
        donor.setPhone(phone);
        donor.setEmail(email);
        donor.setAddress(address);
        donor.setLastDonationDate(lastDonationDate);

        boolean success = donorDAO.addDonor(donor);
        if (success) {
            redirectWithMessage(response, "donor.html", "Thank you for registering as a donor!", "success");
        } else {
            redirectWithMessage(response, "donor.html", "Failed to register donor. Please try again.", "danger");
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void redirectWithMessage(HttpServletResponse response, String page, String message, String type)
            throws IOException {
        response.sendRedirect(page + "?msg=" + URLEncoder.encode(message, "UTF-8") + "&type=" + type);
    }
}
