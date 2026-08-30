package com.bloodbank.servlet;

import com.bloodbank.dao.AdminDAO;
import com.bloodbank.model.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * AdminLoginServlet - authenticates an administrator.
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AdminDAO adminDAO = new AdminDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            redirectWithMessage(response, "admin-login.html", "Please enter username and password.", "danger");
            return;
        }

        Admin admin = adminDAO.loginAdmin(username, password);
        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
            response.sendRedirect("admin-dashboard");
        } else {
            redirectWithMessage(response, "admin-login.html", "Invalid admin credentials.", "danger");
        }
    }

    private void redirectWithMessage(HttpServletResponse response, String page, String message, String type)
            throws IOException {
        response.sendRedirect(page + "?msg=" + java.net.URLEncoder.encode(message, "UTF-8") + "&type=" + type);
    }
}
