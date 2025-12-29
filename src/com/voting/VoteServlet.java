package com.voting;

<<<<<<< HEAD
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");
        res.getWriter().println("SERVLET OK");
=======
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class VoteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String candidateId = request.getParameter("candidate");

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT has_voted FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            boolean hasVoted = false;
            if (rs.next()) {
                hasVoted = rs.getBoolean("has_voted");
            } else {
                PreparedStatement insertUser = conn.prepareStatement(
                    "INSERT INTO users(username, has_voted) VALUES (?, false)");
                insertUser.setString(1, username);
                insertUser.executeUpdate();
            }

            PrintWriter out = response.getWriter();
            response.setContentType("text/html");

            if (hasVoted) {
                out.println("<h2>You have already voted!</h2>");
            } else {
                PreparedStatement voteStmt = conn.prepareStatement(
                    "UPDATE candidates SET votes = votes + 1 WHERE id=?");
                voteStmt.setInt(1, Integer.parseInt(candidateId));
                voteStmt.executeUpdate();

                PreparedStatement updateUser = conn.prepareStatement(
                    "UPDATE users SET has_voted=true WHERE username=?");
                updateUser.setString(1, username);
                updateUser.executeUpdate();

                out.println("<h2>Vote submitted successfully!</h2>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
>>>>>>> a9c9a621962452ebee0527db3f5619d18c954cc9
    }
}
