package com.code;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employee_id");
        String password = request.getParameter("password");

        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin123");

            String query = "SELECT full_name FROM employee WHERE employee_id = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, employeeId);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String fullName = rs.getString("full_name");
                HttpSession session = request.getSession();
                session.setAttribute("employee_id", employeeId);
                session.setAttribute("full_name", fullName);
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("message", "Invalid employee ID or password. Please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
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