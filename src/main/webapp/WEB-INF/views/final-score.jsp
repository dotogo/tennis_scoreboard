<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Final score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h3>
    <a href="${pageContext.request.contextPath}/">
        Tennis Scoreboard
    </a>
</h3>

<h1>Final score</h1>

<div class="scoreboard">
    <c:if test="${not empty matchScoreDto and not empty firstPlayer and not empty secondPlayer}">
        <h4>${firstPlayer.name}: ${matchScoreDto.firstPlayerSets}</h4>
        <h4>${secondPlayer.name}: ${matchScoreDto.secondPlayerSets}</h4>

        <div class="final-image">
            <img src="${pageContext.request.contextPath}/images/final-score.jpg"
                 alt="">
        </div>

        <h2>The match is over</h2>
        <h2>Winner:
            <c:choose>
                <c:when test="${matchScoreDto.firstPlayerSets > matchScoreDto.secondPlayerSets}">
                    ${firstPlayer.name}
                </c:when>
                <c:otherwise>
                    ${secondPlayer.name}
                </c:otherwise>
            </c:choose>
        </h2>
    </c:if>
</div>

<!-- Adding a block with navigation links -->
<div class="nav-links">
    <a href="${pageContext.request.contextPath}/matches">📋 View all matches</a>
    <a href="${pageContext.request.contextPath}/">🎾 Start new match</a>
</div>

</body>
</html>