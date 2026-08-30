package com.bloodbank.servlet;

import com.bloodbank.dao.UserDAO;
import com.bloodbank.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * UserLoginServlet - authenticates a registered user.
 */
@WebServlet("/LoginServlet")
public class UserLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            redirectWithMessage(response, "login.html", "Please enter email and password.", "danger");
            return;
        }

        User user = userDAO.loginUser(email, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("index.html");
        } else {
            redirectWithMessage(response, "login.html", "Invalid email or password.", "danger");
        }
    }

    private void redirectWithMessage(HttpServletResponse response, String page, String message, String type)
            throws IOException {
        response.sendRedirect(page + "?msg=" + URLEncoder.encode(message, "UTF-8") + "&type=" + type);
    }
}
