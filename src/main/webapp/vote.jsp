<%@ page import="java.util.ArrayList" %>
<%@ page import="com.voting.dao.CandidateDAO" %>
<%@ page import="com.voting.model.Candidate" %>
<%@ page import="com.voting.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vote</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<%
    
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        response.sendRedirect("login.html");
        return;
    }
%>

<h2>Welcome, <%= user.getName() %></h2>
<h3>Select a candidate and vote</h3>

<form action="vote" method="post">
<%
    CandidateDAO dao = new CandidateDAO();
    ArrayList<Candidate> list = dao.getAllCandidates();

    for (Candidate c : list) {
%>
        <input type="radio" name="candidateId" value="<%= c.getCandidateId() %>" required>
        <%= c.getName() %><br><br>
<%
    }
%>
    <button type="submit">Submit Vote</button>
</form>

</body>
</html>
