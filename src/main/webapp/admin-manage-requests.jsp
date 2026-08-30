<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.bloodbank.model.BloodRequest" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Requests | Blood Bank Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand fw-bold" href="admin-dashboard">Admin Dashboard</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#adminNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="adminNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="admin-dashboard">Dashboard</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-donors">Manage Donors</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-stock">Manage Blood Stock</a></li>
                    <li class="nav-item"><a class="nav-link active" href="admin-manage-requests">Manage Requests</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-users">Manage Users</a></li>
                    <li class="nav-item"><a class="nav-link text-warning" href="LogoutServlet">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <%
        List<BloodRequest> requests = (List<BloodRequest>) request.getAttribute("requests");
        String message = (String) request.getAttribute("message");
        String alertType = (String) request.getAttribute("alertType");
    %>

    <div class="container my-5">
        <h2 class="mb-4">Manage Blood Requests</h2>

        <% if (message != null) { %>
            <div class="alert alert-<%= alertType %>"><%= message %></div>
        <% } %>

        <div class="card shadow-sm">
            <div class="card-header bg-dark text-white">All Requests</div>
            <div class="card-body table-responsive">
                <table class="table table-hover align-middle">
                    <thead>
                        <tr>
                            <th>ID</th><th>Patient</th><th>Group</th><th>Units</th>
                            <th>Hospital</th><th>Contact</th><th>Date</th><th>Status</th><th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (requests != null) {
                                if (requests.isEmpty()) {
                        %>
                            <tr><td colspan="9" class="text-center text-muted">No requests found.</td></tr>
                        <%
                                } else {
                                    for (BloodRequest r : requests) {
                                        String statusClass = "bg-secondary";
                                        if ("Approved".equals(r.getStatus())) statusClass = "bg-success";
                                        else if ("Rejected".equals(r.getStatus())) statusClass = "bg-danger";
                                        else if ("Pending".equals(r.getStatus())) statusClass = "bg-warning";
                        %>
                        <tr>
                            <td><%= r.getId() %></td>
                            <td><%= r.getPatientName() %></td>
                            <td><span class="badge bg-danger"><%= r.getBloodGroup() %></span></td>
                            <td><%= r.getRequiredUnits() %></td>
                            <td><%= r.getHospitalName() %></td>
                            <td><%= r.getContactNumber() %></td>
                            <td><%= r.getRequestDate() %></td>
                            <td><span class="badge <%= statusClass %>"><%= r.getStatus() %></span></td>
                            <td>
                                <% if ("Pending".equals(r.getStatus())) { %>
                                    <a href="admin-manage-requests?action=approve&id=<%= r.getId() %>"
                                       class="btn btn-sm btn-success">Approve</a>
                                    <a href="admin-manage-requests?action=reject&id=<%= r.getId() %>"
                                       class="btn btn-sm btn-warning">Reject</a>
                                <% } %>
                                <a href="admin-manage-requests?action=delete&id=<%= r.getId() %>"
                                   class="btn btn-sm btn-danger" onclick="return confirm('Delete this request?');">Delete</a>
                            </td>
                        </tr>
                        <%      }
                                }
                            } %>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="alert alert-info mt-4">
            <strong>Note:</strong> When a request is <strong>approved</strong>, the required units are automatically
            deducted from the matching blood group's stock.
        </div>
    </div>

    <footer class="footer bg-dark text-white pt-4 pb-3 mt-5">
        <div class="container text-center">
            <p class="mb-1">&copy; 2026 Blood Bank Management System. Admin Panel.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
