<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/matches-style.css">
</head>
<body>
<h1>Matches</h1>

<table class="matches-table">
    <thead>
    <tr>
        <th>First Player</th>
        <th>Second Player</th>
        <th>Winner</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="match" items="${matches.items}">
        <tr>
            <td>${match.firstPlayerName}</td>
            <td>${match.secondPlayerName}</td>
            <td>${match.winnerName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${empty matches}">
    <p class="no-matches">No matches found.</p>
</c:if>

<div class="nav-links">
    <a href="${pageContext.request.contextPath}/">🎾 Start new match</a>
</div>

</body>
</html>