package com.bloodbank.servlet;

import com.bloodbank.dao.UserDAO;
import com.bloodbank.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * AdminUserServlet - lists and deletes registered users from the admin panel.
 */
@WebServlet("/admin-manage-users")
public class AdminUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (userDAO.deleteUser(id)) {
                request.setAttribute("message", "User deleted successfully.");
                request.setAttribute("alertType", "success");
            } else {
                request.setAttribute("message", "Failed to delete user.");
                request.setAttribute("alertType", "danger");
            }
        }

        List<User> users = userDAO.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("admin-manage-users.jsp").forward(request, response);
    }
}
