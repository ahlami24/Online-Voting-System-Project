package com.voting.servlet;

import com.voting.dao.DBConnection;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT role FROM users WHERE username=? AND password=?"
            );
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                if ("ADMIN".equals(rs.getString("role"))) {
                    response.sendRedirect("admin.jsp");
                } else {
                    response.sendRedirect("voter.jsp");
                }
            } else {
                response.getWriter().println("Invalid credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
