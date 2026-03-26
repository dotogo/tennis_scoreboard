<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Match Score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>Match Score</h1>

<c:if test="${not empty match}">
    <table class="score-table">
        <tr>
            <th>Player</th>
            <th>Sets</th>
            <th>Games</th>
            <th>Points</th>
            <th>Action</th>
        </tr>
        <tr>
            <td>${match.firstPlayer.name}</td>
            <td>${match.firstPlayerScore.sets}</td>
            <td>${match.firstPlayerScore.games}</td>
            <td>
                <c:choose>
                    <c:when test="${match.firstPlayerScore.tieBreak > 0}">
                        ${match.firstPlayerScore.tieBreak} (TB)
                    </c:when>
                    <c:otherwise>
                        ${match.firstPlayerScore.points.value}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">
                    <input type="hidden" name="point-winner" value="player1">
                    <button type="submit" class="action-button">+ Point</button>
                </form>
            </td>
        </tr>
        <tr>
            <td>${match.secondPlayer.name}</td>
            <td>${match.secondPlayerScore.sets}</td>
            <td>${match.secondPlayerScore.games}</td>
            <td>
                <c:choose>
                    <c:when test="${match.secondPlayerScore.tieBreak > 0}">
                        ${match.secondPlayerScore.tieBreak} (TB)
                    </c:when>
                    <c:otherwise>
                        ${match.secondPlayerScore.points.value}
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">
                    <input type="hidden" name="point-winner" value="player2">
                    <button type="submit" class="action-button">+ Point</button>
                </form>
            </td>
        </tr>
    </table>
</c:if>

<c:if test="${empty match}">
    <p>Match not found or completed.</p>
    <a href="<c:url value='/'/>">Back to main page</a>
</c:if>

<script src="${pageContext.request.contextPath}/js/match-score.js"></script>
</body>
</html>