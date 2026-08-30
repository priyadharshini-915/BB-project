<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.bloodbank.model.BloodStock" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blood Availability | Blood Bank Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-danger">
        <div class="container">
            <a class="navbar-brand fw-bold" href="index.html">
                <span class="drop-circle">&#9825;</span> Blood Bank Management System
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navMenu">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="index.html#about">About</a></li>
                    <li class="nav-item"><a class="nav-link active" href="blood-availability">Blood Availability</a></li>
                    <li class="nav-item"><a class="nav-link" href="donor.html">Donor Registration</a></li>
                    <li class="nav-item"><a class="nav-link" href="blood-request.html">Blood Request</a></li>
                    <li class="nav-item"><a class="nav-link" href="admin-login.html">Admin</a></li>
                    <li class="nav-item"><a class="nav-link" href="login.html">Login</a></li>
                    <li class="nav-item"><a class="nav-link" href="register.html">Register</a></li>
                    <li class="nav-item"><a class="nav-link" href="index.html#contact">Contact</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Page Header -->
    <header class="page-header">
        <div class="container text-center text-white">
            <h1>Blood Availability</h1>
            <p>Current stock of blood units available in our bank</p>
        </div>
    </header>

    <div class="container my-5">
        <%
            List<BloodStock> stockList = (List<BloodStock>) request.getAttribute("stockList");
            String allGroups[] = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        %>

        <!-- Search / Filter -->
        <div class="card shadow-sm mb-4">
            <div class="card-body">
                <form class="row g-2 align-items-center" onsubmit="filterGroups(); return false;">
                    <div class="col-md-4">
                        <label class="form-label mb-0">Search by Blood Group</label>
                    </div>
                    <div class="col-md-6">
                        <select class="form-select" id="groupFilter">
                            <option value="">All Blood Groups</option>
                            <% for (String g : allGroups) { %>
                                <option value="<%= g %>"><%= g %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="col-md-2 d-grid">
                        <button type="submit" class="btn btn-danger">Search</button>
                    </div>
                </form>
            </div>
        </div>

        <div id="msgBox"></div>

        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-4" id="stockCards">
            <%
                for (String group : allGroups) {
                    int units = 0;
                    if (stockList != null) {
                        for (BloodStock s : stockList) {
                            if (group.equals(s.getBloodGroup())) {
                                units = s.getUnits();
                            }
                        }
                    }
                    String badge = (units > 0) ? "bg-success" : "bg-secondary";
            %>
            <div class="col blood-group-card" data-group="<%= group %>">
                <div class="card h-100 shadow-sm text-center">
                    <div class="card-body">
                        <h2 class="display-6 text-danger fw-bold"><%= group %></h2>
                        <hr>
                        <h4 class="<%= badge %> text-white d-inline-block px-3 py-1 rounded">
                            <%= units %> Units
                        </h4>
                        <p class="mt-3 mb-0 text-muted">
                            <%= units > 0 ? "Available" : "Currently Unavailable" %>
                        </p>
                    </div>
                </div>
            </div>
            <% } %>
        </div>

        <!-- No-result message -->
        <div id="noResult" class="alert alert-warning d-none text-center mt-4">
            No blood stock found for the selected group.
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer bg-dark text-white pt-4 pb-3 mt-5">
        <div class="container text-center">
            <p class="mb-1">&copy; 2026 Blood Bank Management System. All rights reserved.</p>
            <small>A College Full-Stack Project using Java Servlets, JDBC &amp; MySQL.</small>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="js/script.js"></script>
</body>
</html>
