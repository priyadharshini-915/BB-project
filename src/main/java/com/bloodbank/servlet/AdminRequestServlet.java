package com.bloodbank.servlet;

import com.bloodbank.dao.BloodRequestDAO;
import com.bloodbank.dao.BloodStockDAO;
import com.bloodbank.model.BloodRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * AdminRequestServlet - manages blood requests (approve, reject, delete).
 * When a request is approved, the matching blood stock is deducted.
 */
@WebServlet("/admin-manage-requests")
public class AdminRequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BloodRequestDAO requestDAO = new BloodRequestDAO();
    private BloodStockDAO stockDAO = new BloodStockDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("approve".equals(action)) {
            handleApproval(request);
        } else if ("reject".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (requestDAO.updateRequestStatus(id, "Rejected")) {
                setMessage(request, "Request rejected.", "warning");
            } else {
                setMessage(request, "Failed to reject request.", "danger");
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (requestDAO.deleteRequest(id)) {
                setMessage(request, "Request deleted successfully.", "success");
            } else {
                setMessage(request, "Failed to delete request.", "danger");
            }
        }

        List<BloodRequest> requests = requestDAO.getAllRequests();
        request.setAttribute("requests", requests);
        request.getRequestDispatcher("admin-manage-requests.jsp").forward(request, response);
    }

    /**
     * Approves a request and deducts units from blood stock.
     * The request is approved only if enough units are available.
     */
    private void handleApproval(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        BloodRequest req = findRequestById(id);
        if (req == null) {
            setMessage(request, "Request not found.", "danger");
            return;
        }

        // Deduct units from matching blood group stock first
        boolean deducted = stockDAO.deductUnits(req.getBloodGroup(), req.getRequiredUnits());
        if (!deducted) {
            setMessage(request, "Not enough blood units available for group " + req.getBloodGroup() + ".", "danger");
            return;
        }

        if (requestDAO.updateRequestStatus(id, "Approved")) {
            setMessage(request, "Request approved and blood stock updated.", "success");
        } else {
            setMessage(request, "Failed to approve request.", "danger");
        }
    }

    private BloodRequest findRequestById(int id) {
        for (BloodRequest r : requestDAO.getAllRequests()) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    private void setMessage(HttpServletRequest request, String message, String type) {
        request.setAttribute("message", message);
        request.setAttribute("alertType", type);
    }
}
