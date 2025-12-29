package com.voting.servlet;

import com.voting.dao.CandidateDAO;
import com.voting.dao.UserDAO;
import com.voting.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    private CandidateDAO candidateDAO;
    private UserDAO userDAO;

    @Override
    public void init() {
        candidateDAO = new CandidateDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");

        if (user == null) {
            response.sendRedirect("login.jsp"); // user not logged in
            return;
        }

        int candidateId = Integer.parseInt(request.getParameter("candidateId"));

        candidateDAO.vote(candidateId);

        userDAO.setVoted(user.getVoterId());

        user.setHasVoted(true);
        session.setAttribute("currentUser", user);

        response.sendRedirect("result.jsp");
    }
}
