<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Match Score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>Match Score</h1>

<div class="matches-image">
    <img src="${pageContext.request.contextPath}/images/match-score-img.jpg"
         alt="">
</div>

<div class="match-score">

    <c:if test="${not empty matchScoreDto and not empty firstPlayer and not empty secondPlayer}">
        <table class="score-table">
            <tr>
                <th>Player</th>
                <th>Sets</th>
                <th>Games</th>
                <th>Points</th>
                <th>Action</th>
            </tr>
            <tr>
                <td>${firstPlayer.name}</td>
                <td>${matchScoreDto.firstPlayerSets}</td>
                <td>${matchScoreDto.firstPlayerGames}</td>
                <td>
                    <c:choose>
                        <c:when test="${matchScoreDto.tieBreak}">
                            ${matchScoreDto.firstPlayerPoints} (TB)
                        </c:when>
                        <c:otherwise>
                            ${matchScoreDto.firstPlayerPoints}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">
                        <input type="hidden" name="point-winner" value="${firstPlayer.id}">
                        <button type="submit" class="action-button">+ Point</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>${secondPlayer.name}</td>
                <td>${matchScoreDto.secondPlayerSets}</td>
                <td>${matchScoreDto.secondPlayerGames}</td>
                <td>
                    <c:choose>
                        <c:when test="${matchScoreDto.tieBreak}">
                            ${matchScoreDto.secondPlayerPoints} (TB)
                        </c:when>
                        <c:otherwise>
                            ${matchScoreDto.secondPlayerPoints}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form class="action-form" method="post" action="<c:url value='/match-score?uuid=${param.uuid}'/>">
                        <input type="hidden" name="point-winner" value="${secondPlayer.id}">
                        <button type="submit" class="action-button">+ Point</button>
                    </form>
                </td>
            </tr>
        </table>
    </c:if>

    <c:if test="${empty matchScoreDto or empty firstPlayer or empty secondPlayer}">
        <p>Match not found or completed.</p>
        <a href="<c:url value='/'/>">Back to main page</a>
    </c:if>
</div>


<script src="${pageContext.request.contextPath}/js/match-score.js"></script>
</body>
</html>