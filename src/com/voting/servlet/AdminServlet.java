package com.voting.servlet;

import com.voting.dao.CandidateDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private CandidateDAO candidateDAO;

    @Override
    public void init() {
        candidateDAO = new CandidateDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("addCandidate".equals(action)) {
            String name = request.getParameter("name");
            boolean success = candidateDAO.addCandidate(name);

            if (success) {
                response.sendRedirect("admin.jsp?msg=Candidate Added");
            } else {
                response.sendRedirect("admin.jsp?error=1");
            }
        }
    }
}
