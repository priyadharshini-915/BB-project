/* =====================================================
   Blood Bank Management System - Main JavaScript
   ===================================================== */

// Runs when the page is fully loaded
document.addEventListener('DOMContentLoaded', function () {
    displayMessage();      // Show success/error message from URL query parameters
    setupFormValidation(); // Wire up frontend validation on forms
});

/**
 * Reads the "msg" and "type" query parameters from the URL
 * (set by servlets after a redirect) and displays a Bootstrap alert.
 */
function displayMessage() {
    var params = new URLSearchParams(window.location.search);
    var msg = params.get('msg');
    var type = params.get('type') || 'info';

    var msgBox = document.getElementById('msgBox');
    if (msg && msgBox) {
        var alert = document.createElement('div');
        alert.className = 'alert alert-' + type + ' alert-dismissible fade show';
        alert.setAttribute('role', 'alert');
        alert.innerHTML = msg +
            '<button type="button" class="btn-close" data-bs-dismiss="alert"></button>';
        msgBox.appendChild(alert);
    }
}

/**
 * Sets up input validation for the register form.
 * Checks email format, phone format, and password confirmation.
 */
function setupFormValidation() {
    var registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            var email = registerForm.email.value.trim();
            var phone = registerForm.phone.value.trim();
            var password = registerForm.password.value;
            var confirmPassword = registerForm.confirmPassword.value;

            if (!isValidEmail(email)) {
                showInlineError(registerForm, 'Please enter a valid email address.');
                event.preventDefault();
                return;
            }
            if (!isValidPhone(phone)) {
                showInlineError(registerForm, 'Please enter a valid phone number (10 digits).');
                event.preventDefault();
                return;
            }
            if (password !== confirmPassword) {
                showInlineError(registerForm, 'Passwords do not match.');
                event.preventDefault();
                return;
            }
        });
    }
}

/** Validates a basic email format. */
function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

/** Validates a 10-digit phone number. */
function isValidPhone(phone) {
    return /^[0-9]{10}$/.test(phone);
}

/**
 * Displays an error message inside the form's message box,
 * preventing an empty submission.
 */
function showInlineError(form, message) {
    var msgBox = document.getElementById('msgBox');
    if (msgBox) {
        msgBox.innerHTML = '<div class="alert alert-danger">' + message + '</div>';
    }
}

/**
 * Filters the blood group cards on the availability page
 * based on the selected blood group.
 */
function filterGroups() {
    var filter = document.getElementById('groupFilter').value;
    var cards = document.querySelectorAll('.blood-group-card');
    var visible = 0;

    cards.forEach(function (card) {
        if (filter === '' || card.getAttribute('data-group') === filter) {
            card.style.display = '';
            visible++;
        } else {
            card.style.display = 'none';
        }
    });

    var noResult = document.getElementById('noResult');
    if (noResult) {
        noResult.classList.toggle('d-none', visible > 0);
    }
}

/**
 * Handles the contact form submission on the home page.
 */
function submitContact() {
    var name = document.getElementById('contactName').value.trim();
    var email = document.getElementById('contactEmail').value.trim();
    var message = document.getElementById('contactMessage').value.trim();
    var box = document.getElementById('contactMsg');

    if (!name || !email || !message) {
        box.innerHTML = '<div class="alert alert-danger">Please fill in all fields.</div>';
        return;
    }
    if (!isValidEmail(email)) {
        box.innerHTML = '<div class="alert alert-danger">Please enter a valid email address.</div>';
        return;
    }

    box.innerHTML = '<div class="alert alert-success">Thank you ' + name +
        '! Your message has been received. We will contact you soon.</div>';
    document.getElementById('contactName').value = '';
    document.getElementById('contactEmail').value = '';
    document.getElementById('contactMessage').value = '';
}
