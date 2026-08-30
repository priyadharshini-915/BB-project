<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | Blood Bank Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <!-- Admin Navbar -->
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
                    <li class="nav-item"><a class="nav-link" href="admin-manage-users">Manage Users</a></li>
                    <li class="nav-item"><a class="nav-link text-warning" href="LogoutServlet">Logout</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Stats Cards -->
    <div class="container my-5">
        <h2 class="mb-4">Welcome, Admin</h2>
        <div class="row g-4">
            <div class="col-md-3">
                <div class="card stat-card shadow-sm text-center border-danger">
                    <div class="card-body">
                        <h1 class="display-5 text-danger"><%= request.getAttribute("totalDonors") %></h1>
                        <h6 class="text-muted">Total Donors</h6>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card shadow-sm text-center border-warning">
                    <div class="card-body">
                        <h1 class="display-5 text-warning"><%= request.getAttribute("totalUnits") %></h1>
                        <h6 class="text-muted">Total Blood Units</h6>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card shadow-sm text-center border-info">
                    <div class="card-body">
                        <h1 class="display-5 text-info"><%= request.getAttribute("pendingRequests") %></h1>
                        <h6 class="text-muted">Pending Requests</h6>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card stat-card shadow-sm text-center border-success">
                    <div class="card-body">
                        <h1 class="display-5 text-success"><%= request.getAttribute("approvedRequests") %></h1>
                        <h6 class="text-muted">Approved Requests</h6>
                    </div>
                </div>
            </div>
        </div>

        <!-- Admin Actions -->
        <div class="row g-4 mt-3">
            <div class="col-md-3">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <h5>Manage Donors</h5>
                        <p class="text-muted small">Add, edit and delete donors</p>
                        <a href="admin-manage-donors" class="btn btn-danger">Open</a>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <h5>Manage Blood Stock</h5>
                        <p class="text-muted small">Add, update and delete stock</p>
                        <a href="admin-manage-stock" class="btn btn-warning">Open</a>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <h5>Manage Requests</h5>
                        <p class="text-muted small">Approve or reject requests</p>
                        <a href="admin-manage-requests" class="btn btn-info">Open</a>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card shadow-sm">
                    <div class="card-body text-center">
                        <h5>Manage Users</h5>
                        <p class="text-muted small">View and delete users</p>
                        <a href="admin-manage-users" class="btn btn-success">Open</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer class="footer bg-dark text-white pt-4 pb-3 mt-5">
        <div class="container text-center">
            <p class="mb-1">&copy; 2026 Blood Bank Management System. Admin Panel.</p>
        </div>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
