<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/matches-style.css">
</head>
<body>
<h1>Matches</h1>

<form action="${pageContext.request.contextPath}/matches" method="get">
    <div class="player-selection">
        <input type="text" name="filter_by_player_name" value="<c:out value='${param.filter_by_player_name}'/>" class="filter-field" >
        <input type="hidden" name="page" value="1">
        <a href="${pageContext.request.contextPath}/matches" class="reset-button">Reset Filter</a>
    </div>
</form>

<c:choose>
    <c:when test="${empty matches}">
        <p class="no-matches">No matches found.</p>
    </c:when>

    <c:otherwise>
        <table class="matches-table">
            <thead>
            <tr>
                <th>First Player</th>
                <th>Second Player</th>
                <th>Winner</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="match" items="${matches.items}" varStatus="status">
                <tr>
                    <td>${match.firstPlayerName}</td>
                    <td>${match.secondPlayerName}</td>
                    <td class="winner-name">
                            <span class="winner-badge ${status.index % 2 == 0 ? 'odd' : 'even'}">
                                    ${match.winnerName}
                            </span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>


<div class="nav-links">
    <a href="${pageContext.request.contextPath}/">🎾 Start new match</a>
</div>

</body>
</html>