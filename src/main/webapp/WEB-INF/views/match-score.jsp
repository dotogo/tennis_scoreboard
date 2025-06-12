<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Match Score</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .score-table {
            width: 80%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        .score-table th, .score-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        .score-table th {
            background-color: #f2f2f2;
        }
        .action-button {
            padding: 6px 12px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .action-button:hover {
            background-color: #45a049;
        }
        .action-form {
            display: inline;
            margin: 0;
            padding: 0;
        }
    </style>
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
                        ${match.firstPlayerScore.tieBreak}
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
                        ${match.secondPlayerScore.tieBreak}
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
</body>
</html>