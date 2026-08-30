package com.bloodbank.servlet;

import com.bloodbank.dao.BloodRequestDAO;
import com.bloodbank.model.BloodRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * BloodRequestServlet - handles submission of blood requests by users.
 */
@WebServlet("/BloodRequestServlet")
public class BloodRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BloodRequestDAO requestDAO = new BloodRequestDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String patientName = request.getParameter("patientName");
        String bloodGroup = request.getParameter("bloodGroup");
        String requiredUnits = request.getParameter("requiredUnits");
        String hospitalName = request.getParameter("hospitalName");
        String contactNumber = request.getParameter("contactNumber");
        String requestDate = request.getParameter("requestDate");
        String address = request.getParameter("address");
        String reason = request.getParameter("reason");

        if (isEmpty(patientName) || isEmpty(bloodGroup) || isEmpty(requiredUnits)
                || isEmpty(hospitalName) || isEmpty(contactNumber)) {
            redirectWithMessage(response, "blood-request.html", "All required fields must be filled.", "danger");
            return;
        }

        BloodRequest req = new BloodRequest();
        req.setPatientName(patientName);
        req.setBloodGroup(bloodGroup);
        req.setRequiredUnits(Integer.parseInt(requiredUnits));
        req.setHospitalName(hospitalName);
        req.setContactNumber(contactNumber);
        req.setRequestDate(requestDate);
        req.setAddress(address);
        req.setReason(reason);

        boolean success = requestDAO.submitRequest(req);
        if (success) {
            redirectWithMessage(response, "blood-request.html",
                    "Blood request submitted successfully. It is now pending approval.", "success");
        } else {
            redirectWithMessage(response, "blood-request.html", "Failed to submit request. Please try again.", "danger");
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
