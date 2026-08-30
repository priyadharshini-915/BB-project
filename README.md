# 🩸 Blood Bank Management System

A complete full-stack **Blood Bank Management System** built as a college project. Users can register and log in, donors can register, users can check blood availability and submit blood requests, and admins can manage donors, blood stock, blood requests, and users.

The application is built using **Java Servlets + JDBC + MySQL** on the backend and **HTML5 + CSS3 + Bootstrap 5 + JavaScript** on the frontend, deployed on **Apache Tomcat**.

---

## Features

- User registration and login (passwords hashed with SHA-256)
- Donor registration
- Blood availability display with search/filter by blood group
- Blood request submission
- Separate admin login and admin dashboard
- Admin manages:
  - Donors (view / add / update / delete)
  - Blood stock (view / add / update / delete)
  - Blood requests (approve / reject / delete)
  - Users (view / delete)
- Automatic blood-stock deduction when a request is approved
- Frontend validation (email, phone, password confirmation)
- Responsive, professional Bootstrap 5 design

---

## Technologies Used

| Layer      | Technology                          |
|------------|-------------------------------------|
| Frontend   | HTML5, CSS3, Bootstrap 5, JavaScript |
| Backend    | Java, Java Servlets (Jakarta EE), JDBC |
| Database   | MySQL                                |
| Server     | Apache Tomcat 10+                    |
| IDE        | VS Code                              |

---

## Project Structure

```
BB-project/
│
├── src/
│   └── main/
│       ├── java/com/bloodbank/
│       │   ├── model/     (User, Donor, BloodStock, BloodRequest, Admin)
│       │   ├── dao/       (Data Access Objects - JDBC queries)
│       │   ├── servlet/   (Controllers - handle HTTP requests)
│       │   └── util/      (DBConnection, PasswordUtil)
│       └── webapp/
│           ├── index.html
│           ├── login.html
│           ├── register.html
│           ├── donor.html
│           ├── blood-request.html
│           ├── blood-availability.html / .jsp
│           ├── admin-login.html
│           ├── admin-dashboard.html / .jsp
│           ├── admin-manage-*.jsp
│           ├── css/style.css
│           ├── js/script.js
│           └── WEB-INF/web.xml
│
├── database/
│   └── blood_bank_db.sql
│
├── README.md
└── .gitignore
```

> **Note about HTML vs JSP:** Static pages (home, forms) are plain `.html`. Pages that must show live database data (blood availability and all admin pages) are rendered by servlets using `.jsp` files. The `.html` shells for these just redirect to the functional servlet-driven pages.

---

## Modules

1. **Home** – Navbar, hero section, blood donation awareness, about & contact sections.
2. **User Registration** – Full name, email, phone, password + confirm, blood group, address.
3. **User Login** – Email + password, authenticated against MySQL.
4. **Donor Registration** – Donor name, age, gender, blood group, phone, email, address, last donation date.
5. **Blood Availability** – Shows stock for A+, A-, B+, B-, AB+, AB-, O+, O- with search/filter.
6. **Blood Request** – Patient name, blood group, required units, hospital, contact, date, address, reason.
7. **Admin Login** – Separate credentials for administrators.
8. **Admin Dashboard** – Shows total donors, total units, pending requests, approved requests + navigation.
9. **Blood Stock Management** – Add / update / delete / view stock.
10. **Donor Management** – View / add / update / delete donors.
11. **Request Management** – Approve / reject / delete requests. Approval deducts stock.

---

## Database Setup

1. Install and start **MySQL** (e.g., XAMPP or MySQL Server).
2. Open the MySQL command line or a tool like **MySQL Workbench** / **phpMyAdmin**.
3. Run the provided script:

```sql
source database/blood_bank_db.sql;
```

This creates the `blood_bank_db` database, all tables, and sample data.

### Sample Login Credentials

| Role  | Username / Email   | Password     |
|-------|--------------------|--------------|
| Admin | `admin`            | `admin123`   |
| User  | `john.doe@example.com` | `password123` |

---

## How to Run the Project

### 1. Configure the Database Connection

Open `src/main/java/com/bloodbank/util/DBConnection.java` and set your MySQL credentials:

```java
private static final String DB_URL      = "jdbc:mysql://localhost:3306/blood_bank_db?useSSL=false&serverTimezone=UTC";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = "your_password";
```

### 2. Add the required JARs

- **MySQL Connector/J** (`mysql-connector-j-8.x.x.jar`) – download from [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/).
- **Servlet API** – already present inside Tomcat (`lib/servlet-api.jar`). Do **not** bundle it in the `.war`.

Place `mysql-connector-j-8.x.x.jar` into `WEB-INF/lib/` (i.e., `src/main/webapp/WEB-INF/lib/`).

### 3. Compile the Java sources

Compile the Java classes against both the servlet API and MySQL connector:

```bash
# From the project root (replace paths with your Tomcat location)
javac -cp "C:\apache-tomcat-10.x\lib\servlet-api.jar;src\main\webapp\WEB-INF\lib\mysql-connector-j-8.x.x.jar" `
      -d build/classes `
      (Get-ChildItem -Recurse src\main\java -Filter *.java).FullName
```

### 4. Assemble the web application

Copy these into a `build/bloodbank/` folder:

- `build/classes/**` → `build/bloodbank/WEB-INF/classes/`
- `src/main/webapp/**` → `build/bloodbank/` (the HTML/CSS/JS/JSP files)
- `mysql-connector-j-8.x.x.jar` → `build/bloodbank/WEB-INF/lib/`

Then package it:

```bash
jar -cvf bloodbank.war -C build/bloodbank .
```

### 5. Deploy to Tomcat

Copy `bloodbank.war` into Tomcat's `webapps/` folder and start Tomcat (via `startup.bat` on Windows or `catalina.sh` on Linux/Mac). You can also deploy via the Tomcat Manager web app.

Open the browser:

```
http://localhost:8080/bloodbank/
```

---

## How to Configure MySQL

1. Start MySQL and run `database/blood_bank_db.sql`.
2. Make sure your MySQL username/password match the values in `DBConnection.java`.
3. If you use a different port (e.g. 3307), update the `DB_URL`.

## How to Configure Tomcat

1. Download and extract Apache Tomcat 10+.
2. Confirm Tomcat works by opening `http://localhost:8080/` (shows the default page).
3. Deploy the `.war` file into the `webapps` folder.
4. Access the app at `http://localhost:8080/bloodbank/`.

---

## How to Test the Project

1. **Home page** loads and navigation works.
2. **Register** a new user → redirected to login with a success message.
3. **Login** as a registered user → goes back to home page.
4. **Donor Registration** saves a new donor; confirm it appears in admin → Manage Donors.
5. **Blood Availability** shows stock from the database; test searching a group.
6. **Blood Request** submit a request → appears as *Pending* in admin → Manage Requests.
7. **Admin Login** use `admin / admin123`.
8. **Approve a request** → stock for that group decreases; status becomes *Approved*.
9. **Add/Update/Delete** donors and stock from the admin panel.

---

## Troubleshooting

- **"Connection error"** → check `DBConnection.java` credentials, MySQL is running, and the JDBC driver jar is in `WEB-INF/lib`.
- **"ClassNotFound: com.mysql.cj.jdbc.Driver"** → MySQL connector jar is missing or not on the classpath.
- **Pages not updating** → make sure you redeployed the `.war` / restarted Tomcat.

---

## Git / GitHub Commands

```bash
# Initialize the repository
git init

# Stage all files
git add .

# First commit
git commit -m "Initial commit: Blood Bank Management System"

# Rename the default branch to main
git branch -M main

# Connect to your remote repository (replace with your GitHub URL)
git remote add origin https://github.com/YOUR_USERNAME/blood-bank-management-system.git

# Push to GitHub
git push -u origin main
```

> Only run the `git push` command when you are ready to upload to GitHub.

---

*Built with Java Servlets, JDBC, MySQL, HTML, CSS, Bootstrap 5 and JavaScript – a complete full-stack college project.*
