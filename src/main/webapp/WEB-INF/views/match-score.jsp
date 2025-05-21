<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--&lt;%&ndash;<%@ taglib prefix="c" uri="https://jakarta.ee/xml/ns/jakartaee/jstl/core" %>&ndash;%&gt;--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Теннисный матч</title>--%>
<%--    <style>--%>
<%--        body {--%>
<%--            font-family: Arial, sans-serif;--%>
<%--            margin: 20px;--%>
<%--        }--%>
<%--        .score-table {--%>
<%--            width: 100%;--%>
<%--            border-collapse: collapse;--%>
<%--            margin-bottom: 20px;--%>
<%--        }--%>
<%--        .score-table th, .score-table td {--%>
<%--            border: 1px solid #ddd;--%>
<%--            padding: 8px;--%>
<%--            text-align: center;--%>
<%--        }--%>
<%--        .score-table th {--%>
<%--            background-color: #f2f2f2;--%>
<%--        }--%>
<%--        .action-form {--%>
<%--            margin-top: 10px;--%>
<%--        }--%>
<%--        .action-button {--%>
<%--            padding: 8px 16px;--%>
<%--            margin: 0 5px;--%>
<%--            background-color: #4CAF50;--%>
<%--            color: white;--%>
<%--            border: none;--%>
<%--            border-radius: 4px;--%>
<%--            cursor: pointer;--%>
<%--        }--%>
<%--        .action-button:hover {--%>
<%--            background-color: #45a049;--%>
<%--        }--%>
<%--    </style>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Счёт матча</h1>--%>

<%--<c:if test="${not empty match}">--%>
<%--    <table class="score-table">--%>
<%--        <tr>--%>
<%--            <th>Player</th>--%>
<%--            <th>Sets</th>--%>
<%--            <th>Games</th>--%>
<%--            <th>Points</th>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>${match.firstPlayer.name}</td>--%>
<%--            <td>${match.matchScore.setsFirstPlayer}</td>--%>
<%--            <td>${match.matchScore.gamesFirstPlayer}</td>--%>
<%--            <td>${match.matchScore.pointsFirstPlayer.value}</td>--%>
<%--        </tr>--%>
<%--        <tr>--%>
<%--            <td>${match.secondPlayer.name}</td>--%>
<%--            <td>${match.matchScore.setsSecondPlayer}</td>--%>
<%--            <td>${match.matchScore.gamesSecondPlayer}</td>--%>
<%--            <td>${match.matchScore.pointsSecondPlayer.value}</td>--%>
<%--        </tr>--%>
<%--    </table>--%>

<%--    <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">--%>
<%--        <input type="hidden" name="winner" value="player1">--%>
<%--        <button type="submit" class="action-button">${match.firstPlayer.name} won a point</button>--%>
<%--    </form>--%>

<%--    <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">--%>
<%--        <input type="hidden" name="winner" value="player2">--%>
<%--        <button type="submit" class="action-button">${match.secondPlayer.name} won a point</button>--%>
<%--    </form>--%>
<%--</c:if>--%>

<%--<c:if test="${empty match}">--%>
<%--    <p>Match not found or completed.</p>--%>
<%--    <a href="<c:url value='/'/>">Back to main page</a>--%>
<%--</c:if>--%>
<%--</body>--%>
<%--</html>--%>


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
            <td>${match.matchScore.setsFirstPlayer}</td>
            <td>${match.matchScore.gamesFirstPlayer}</td>
            <td>${match.matchScore.pointsFirstPlayer.value}</td>
            <td>
                <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">
                    <input type="hidden" name="winner" value="player1">
                    <button type="submit" class="action-button">+ Point</button>
                </form>
            </td>
        </tr>
        <tr>
            <td>${match.secondPlayer.name}</td>
            <td>${match.matchScore.setsSecondPlayer}</td>
            <td>${match.matchScore.gamesSecondPlayer}</td>
            <td>${match.matchScore.pointsSecondPlayer.value}</td>
            <td>
                <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">
                    <input type="hidden" name="winner" value="player2">
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