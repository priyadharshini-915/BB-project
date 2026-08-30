package com.bloodbank.servlet;

import com.bloodbank.dao.UserDAO;
import com.bloodbank.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * UserRegisterServlet - handles new user registration.
 * Displays feedback messages via query parameters.
 */
@WebServlet("/RegisterServlet")
public class UserRegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String bloodGroup = request.getParameter("bloodGroup");
        String address = request.getParameter("address");

        if (isEmpty(fullName) || isEmpty(email) || isEmpty(phone) || isEmpty(password)
                || isEmpty(bloodGroup) || isEmpty(address)) {
            redirectWithMessage(response, "register.html", "All fields are required!", "danger");
            return;
        }

        if (!password.equals(confirmPassword)) {
            redirectWithMessage(response, "register.html", "Passwords do not match!", "danger");
            return;
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setBloodGroup(bloodGroup);
        user.setAddress(address);

        boolean success = userDAO.registerUser(user);
        if (success) {
            redirectWithMessage(response, "login.html", "Registration successful! Please login.", "success");
        } else {
            redirectWithMessage(response, "register.html",
                    "Registration failed. Email may already be registered.", "danger");
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
