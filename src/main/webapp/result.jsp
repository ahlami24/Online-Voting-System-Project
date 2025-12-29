<%@ page import="java.util.ArrayList" %>
<%@ page import="com.voting.dao.CandidateDAO" %>
<%@ page import="com.voting.model.Candidate" %>
<%@ page import="com.voting.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Voting Results</title>
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

<h2>Voting Results</h2>

<table border="1" cellpadding="10">
    <tr>
        <th>Candidate Name</th>
        <th>Total Votes</th>
    </tr>

<%
    CandidateDAO dao = new CandidateDAO();
    ArrayList<Candidate> list = dao.getAllCandidates();

    for (Candidate c : list) {
%>
    <tr>
        <td><%= c.getName() %></td>
        <td><%= c.getVotes() %></td>
    </tr>
<%
    }
%>

</table>

<br>
<a href="login.html">Logout</a>

</body>
</html>
