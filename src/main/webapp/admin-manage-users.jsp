<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.bloodbank.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users | Blood Bank Management System</title>
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
                    <li class="nav-item"><a class="nav-link" href="admin-manage-requests">Manage Requests</a></li>
                    <li class="nav-item"><a class="nav-link active" href="admin-manage-users">Manage Users</a></li>
                    <li class="nav-item"><a class="nav-link text-warning" href="LogoutServlet">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <%
        List<User> users = (List<User>) request.getAttribute("users");
        String message = (String) request.getAttribute("message");
        String alertType = (String) request.getAttribute("alertType");
    %>

    <div class="container my-5">
        <h2 class="mb-4">Manage Users</h2>

        <% if (message != null) { %>
            <div class="alert alert-<%= alertType %>"><%= message %></div>
        <% } %>

        <div class="card shadow-sm">
            <div class="card-header bg-dark text-white">Registered Users</div>
            <div class="card-body table-responsive">
                <table class="table table-hover align-middle">
                    <thead>
                        <tr>
                            <th>ID</th><th>Full Name</th><th>Email</th><th>Phone</th>
                            <th>Blood Group</th><th>Address</th><th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            if (users != null) {
                                if (users.isEmpty()) {
                        %>
                            <tr><td colspan="7" class="text-center text-muted">No users found.</td></tr>
                        <%
                                } else {
                                    for (User u : users) {
                        %>
                        <tr>
                            <td><%= u.getId() %></td>
                            <td><%= u.getFullName() %></td>
                            <td><%= u.getEmail() %></td>
                            <td><%= u.getPhone() %></td>
                            <td><span class="badge bg-danger"><%= u.getBloodGroup() %></span></td>
                            <td><%= u.getAddress() %></td>
                            <td>
                                <a href="admin-manage-users?action=delete&id=<%= u.getId() %>"
                                   class="btn btn-sm btn-danger" onclick="return confirm('Delete this user?');">Delete</a>
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

    <footer class="footer bg-dark text-white pt-4 pb-3 mt-5">
        <div class="container text-center">
            <p class="mb-1">&copy; 2026 Blood Bank Management System. Admin Panel.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
