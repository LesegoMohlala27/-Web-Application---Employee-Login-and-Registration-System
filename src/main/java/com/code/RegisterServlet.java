package com.code;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employee_id");
        String fullName = request.getParameter("full_name");
        String department = request.getParameter("department");
        String role = request.getParameter("role");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin123");

            String checkQuery = "SELECT * FROM employee WHERE employee_id = ? OR email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, employeeId);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("message", "Employee ID or email already exists. Please choose another one.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                String insertEmployeeQuery = "INSERT INTO employee (employee_id, full_name, department, role, password, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement insertEmployeeStmt = conn.prepareStatement(insertEmployeeQuery);
                insertEmployeeStmt.setString(1, employeeId);
                insertEmployeeStmt.setString(2, fullName);
                insertEmployeeStmt.setString(3, department);
                insertEmployeeStmt.setString(4, role);
                insertEmployeeStmt.setString(5, password);
                insertEmployeeStmt.setString(6, phone);
                insertEmployeeStmt.setString(7, email);
                insertEmployeeStmt.executeUpdate();

                String insertLoginQuery = "INSERT INTO login (employee_id, password) VALUES (?, ?)";
                PreparedStatement insertLoginStmt = conn.prepareStatement(insertLoginQuery);
                insertLoginStmt.setString(1, employeeId);
                insertLoginStmt.setString(2, password);
                insertLoginStmt.executeUpdate();

                response.sendRedirect("login.jsp");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}