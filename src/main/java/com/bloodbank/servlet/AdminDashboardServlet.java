package com.bloodbank.servlet;

import com.bloodbank.dao.BloodRequestDAO;
import com.bloodbank.dao.BloodStockDAO;
import com.bloodbank.dao.DonorDAO;
import com.bloodbank.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * AdminDashboardServlet - loads dashboard statistics and forwards
 * to the admin dashboard page. Requires an admin session.
 */
@WebServlet("/admin-dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Protect the dashboard: only accessible when logged in as admin
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("admin-login.html");
            return;
        }

        DonorDAO donorDAO = new DonorDAO();
        BloodStockDAO stockDAO = new BloodStockDAO();
        BloodRequestDAO requestDAO = new BloodRequestDAO();
        UserDAO userDAO = new UserDAO();

        request.setAttribute("totalDonors", donorDAO.countDonors());
        request.setAttribute("totalUnits", stockDAO.countTotalUnits());
        request.setAttribute("pendingRequests", requestDAO.countByStatus("Pending"));
        request.setAttribute("approvedRequests", requestDAO.countByStatus("Approved"));
        request.setAttribute("totalUsers", userDAO.countUsers());

        request.getRequestDispatcher("admin-dashboard.jsp").forward(request, response);
    }
}
