package com.bloodbank.servlet;

import com.bloodbank.dao.BloodStockDAO;
import com.bloodbank.model.BloodStock;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * BloodAvailabilityServlet - loads blood stock and forwards to
 * the blood availability page for display and search.
 */
@WebServlet("/blood-availability")
public class BloodAvailabilityServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BloodStockDAO stockDAO = new BloodStockDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<BloodStock> stockList = stockDAO.getAllBloodStock();
        request.setAttribute("stockList", stockList);
        request.getRequestDispatcher("blood-availability.jsp").forward(request, response);
    }
}
