<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.bloodbank.model.BloodStock" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Blood Stock | Blood Bank Management System</title>
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
                    <li class="nav-item"><a class="nav-link active" href="admin-manage-stock">Manage Blood Stock</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-requests">Manage Requests</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-users">Manage Users</a></li>
                    <li class="nav-item"><a class="nav-link text-warning" href="LogoutServlet">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <%
        List<BloodStock> stockList = (List<BloodStock>) request.getAttribute("stockList");
        BloodStock editing = (BloodStock) request.getAttribute("editingStock");
        String message = (String) request.getAttribute("message");
        String alertType = (String) request.getAttribute("alertType");
    %>

    <div class="container my-5">
        <h2 class="mb-4">Manage Blood Stock</h2>

        <% if (message != null) { %>
            <div class="alert alert-<%= alertType %>"><%= message %></div>
        <% } %>

        <div class="row g-4">
            <!-- Add / Edit Stock Form -->
            <div class="col-md-5">
                <div class="card shadow-sm">
                    <div class="card-header bg-warning text-dark">
                        <%= (editing != null) ? "Edit Blood Stock" : "Add Blood Stock" %>
                    </div>
                    <div class="card-body">
                        <form action="admin-manage-stock" method="post">
                            <input type="hidden" name="action" value="<%= (editing != null) ? "update" : "add" %>">
                            <input type="hidden" name="id" value="<%= (editing != null) ? editing.getId() : "" %>">

                            <div class="mb-3">
                                <label class="form-label">Blood Group *</label>
                                <select name="bloodGroup" class="form-select" required>
                                    <option value="">Select</option>
                                    <% String[] groups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
                                       for (String g : groups) { %>
                                       <option value="<%= g %>"
                                          <%= (editing != null && g.equals(editing.getBloodGroup())) ? "selected" : "" %>><%= g %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Units *</label>
                                <input type="number" name="units" class="form-control" min="0" required
                                       value="<%= (editing != null) ? editing.getUnits() : "" %>">
                            </div>
                            <button type="submit" class="btn btn-warning w-100">
                                <%= (editing != null) ? "Update Stock" : "Add Stock" %>
                            </button>
                            <% if (editing != null) { %>
                                <a href="admin-manage-stock" class="btn btn-secondary w-100 mt-2">Cancel Edit</a>
                            <% } %>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Stock Table -->
            <div class="col-md-7">
                <div class="card shadow-sm">
                    <div class="card-header bg-dark text-white">Blood Stock</div>
                    <div class="card-body table-responsive">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr><th>ID</th><th>Blood Group</th><th>Units</th><th>Updated</th><th>Actions</th></tr>
                            </thead>
                            <tbody>
                                <%
                                    if (stockList != null) {
                                        if (stockList.isEmpty()) {
                                %>
                                    <tr><td colspan="5" class="text-center text-muted">No stock available.</td></tr>
                                <%
                                        } else {
                                            for (BloodStock s : stockList) {
                                %>
                                <tr>
                                    <td><%= s.getId() %></td>
                                    <td><span class="badge bg-danger"><%= s.getBloodGroup() %></span></td>
                                    <td><%= s.getUnits() %></td>
                                    <td><%= s.getUpdatedDate() %></td>
                                    <td>
                                        <a href="admin-manage-stock?action=edit&id=<%= s.getId() %>" class="btn btn-sm btn-warning">Edit</a>
                                        <a href="admin-manage-stock?action=delete&id=<%= s.getId() %>"
                                           class="btn btn-sm btn-danger" onclick="return confirm('Delete this stock?');">Delete</a>
                                    </td>
                                </tr>
                                <%      }
                                        }
                                    } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
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
