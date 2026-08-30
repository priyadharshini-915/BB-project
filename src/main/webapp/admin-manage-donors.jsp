<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.bloodbank.model.Donor" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Donors | Blood Bank Management System</title>
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
                    <li class="nav-item"><a class="nav-link active" href="admin-manage-donors">Manage Donors</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-stock">Manage Blood Stock</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-requests">Manage Requests</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-manage-users">Manage Users</a></li>
                    <li class="nav-item"><a class="nav-link text-warning" href="LogoutServlet">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <%
        List<Donor> donors = (List<Donor>) request.getAttribute("donors");
        Donor editing = (Donor) request.getAttribute("editingDonor");
        String message = (String) request.getAttribute("message");
        String alertType = (String) request.getAttribute("alertType");
    %>

    <div class="container my-5">
        <h2 class="mb-4">Manage Donors</h2>

        <% if (message != null) { %>
            <div class="alert alert-<%= alertType %>"><%= message %></div>
        <% } %>

        <div class="row g-4">
            <!-- Add / Edit Donor Form -->
            <div class="col-md-5">
                <div class="card shadow-sm">
                    <div class="card-header bg-danger text-white">
                        <%= (editing != null) ? "Edit Donor" : "Add New Donor" %>
                    </div>
                    <div class="card-body">
                        <form action="admin-manage-donors" method="post">
                            <input type="hidden" name="action" value="<%= (editing != null) ? "update" : "add" %>">
                            <input type="hidden" name="id" value="<%= (editing != null) ? editing.getId() : "" %>">

                            <div class="mb-3">
                                <label class="form-label">Donor Name *</label>
                                <input type="text" name="donorName" class="form-control" required
                                       value="<%= (editing != null) ? editing.getDonorName() : "" %>">
                            </div>
                            <div class="row">
                                <div class="col mb-3">
                                    <label class="form-label">Age *</label>
                                    <input type="number" name="age" class="form-control" min="18" required
                                           value="<%= (editing != null) ? editing.getAge() : "" %>">
                                </div>
                                <div class="col mb-3">
                                    <label class="form-label">Gender *</label>
                                    <select name="gender" class="form-select" required>
                                        <option value="">Select</option>
                                        <option <%= (editing != null && "Male".equals(editing.getGender())) ? "selected" : "" %>>Male</option>
                                        <option <%= (editing != null && "Female".equals(editing.getGender())) ? "selected" : "" %>>Female</option>
                                        <option <%= (editing != null && "Other".equals(editing.getGender())) ? "selected" : "" %>>Other</option>
                                    </select>
                                </div>
                            </div>
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
                                <label class="form-label">Phone *</label>
                                <input type="tel" name="phone" class="form-control" required
                                       value="<%= (editing != null) ? editing.getPhone() : "" %>">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control"
                                       value="<%= (editing != null) ? editing.getEmail() : "" %>">
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Address *</label>
                                <textarea name="address" class="form-control" rows="2" required><%= (editing != null) ? editing.getAddress() : "" %></textarea>
                            </div>
                            <div class="mb-3">
                                <label class="form-label">Last Donation Date</label>
                                <input type="date" name="lastDonationDate" class="form-control"
                                       value="<%= (editing != null) ? editing.getLastDonationDate() : "" %>">
                            </div>
                            <button type="submit" class="btn btn-danger w-100">
                                <%= (editing != null) ? "Update Donor" : "Add Donor" %>
                            </button>
                            <% if (editing != null) { %>
                                <a href="admin-manage-donors" class="btn btn-secondary w-100 mt-2">Cancel Edit</a>
                            <% } %>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Donors Table -->
            <div class="col-md-7">
                <div class="card shadow-sm">
                    <div class="card-header bg-dark text-white">All Donors</div>
                    <div class="card-body table-responsive">
                        <table class="table table-hover align-middle">
                            <thead>
                                <tr>
                                    <th>ID</th><th>Name</th><th>Age</th><th>Gender</th>
                                    <th>Blood Group</th><th>Phone</th><th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    if (donors != null) {
                                        for (Donor d : donors) {
                                %>
                                <tr>
                                    <td><%= d.getId() %></td>
                                    <td><%= d.getDonorName() %></td>
                                    <td><%= d.getAge() %></td>
                                    <td><%= d.getGender() %></td>
                                    <td><span class="badge bg-danger"><%= d.getBloodGroup() %></span></td>
                                    <td><%= d.getPhone() %></td>
                                    <td>
                                        <a href="admin-manage-donors?action=edit&id=<%= d.getId() %>" class="btn btn-sm btn-warning">Edit</a>
                                        <a href="admin-manage-donors?action=delete&id=<%= d.getId() %>"
                                           class="btn btn-sm btn-danger" onclick="return confirm('Delete this donor?');">Delete</a>
                                    </td>
                                </tr>
                                <%  }
                                    } else { %>
                                <tr><td colspan="7" class="text-center text-muted">No donors found.</td></tr>
                                <% } %>
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
