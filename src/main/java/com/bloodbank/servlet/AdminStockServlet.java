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
 * AdminStockServlet - CRUD operations for blood stock from the admin panel.
 * Action is supplied via the "action" parameter.
 */
@WebServlet("/admin-manage-stock")
public class AdminStockServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BloodStockDAO stockDAO = new BloodStockDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (stockDAO.deleteBloodStock(id)) {
                setMessage(request, "Blood stock deleted successfully.", "success");
            } else {
                setMessage(request, "Failed to delete blood stock.", "danger");
            }
        } else if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            request.setAttribute("editingStock", stockDAO.getBloodStockById(id));
        }

        List<BloodStock> stockList = stockDAO.getAllBloodStock();
        request.setAttribute("stockList", stockList);
        request.getRequestDispatcher("admin-manage-stock.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        BloodStock stock = new BloodStock();
        if (request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            stock.setId(Integer.parseInt(request.getParameter("id")));
        }
        stock.setBloodGroup(request.getParameter("bloodGroup"));
        stock.setUnits(Integer.parseInt(request.getParameter("units")));

        boolean success;
        if ("update".equals(action)) {
            success = stockDAO.updateBloodStock(stock);
        } else {
            success = stockDAO.addBloodStock(stock);
        }
        setMessage(request, success ? "Blood stock saved successfully." : "Operation failed.",
                success ? "success" : "danger");

        request.setAttribute("stockList", stockDAO.getAllBloodStock());
        request.getRequestDispatcher("admin-manage-stock.jsp").forward(request, response);
    }

    private void setMessage(HttpServletRequest request, String message, String type) {
        request.setAttribute("message", message);
        request.setAttribute("alertType", type);
    }
}
