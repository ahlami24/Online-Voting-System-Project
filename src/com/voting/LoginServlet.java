package com.voting.servlet;

import com.voting.dao.DBConnection; // Make sure this file exists
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnection.getConnection(); // DBConnection must exist
            ps = con.prepareStatement("SELECT role FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", rs.getString("role"));

                if ("ADMIN".equalsIgnoreCase(rs.getString("role"))) {
                    response.sendRedirect("admin.jsp");
                } else {
                    response.sendRedirect("voter.jsp");
                }
            } else {
                response.getWriter().println("Invalid username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException ignored) {
            }
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException ignored) {
            }
            try {
                if (con != null)
                    con.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
