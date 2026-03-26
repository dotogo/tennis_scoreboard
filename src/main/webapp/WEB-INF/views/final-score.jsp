<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Final score</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>Final score</h1>
<c:if test="${not empty match}">
    <h3>${match.firstPlayer.name}: ${match.firstPlayerScore.sets}</h3>
    <h3>${match.secondPlayer.name}: ${match.secondPlayerScore.sets}</h3>
    <br>
    <br>

    <h2>The match is over</h2>
    <h2>Winner:
        <c:choose>
            <c:when test="${match.firstPlayerScore.sets > match.secondPlayerScore.sets}">
                ${match.firstPlayer.name}
            </c:when>
            <c:otherwise>
                ${match.secondPlayer.name}
            </c:otherwise>
        </c:choose>
    </h2>
</c:if>

<!-- Adding a block with navigation links -->
<div class="nav-links">
    <a href="${pageContext.request.contextPath}/matches">📋 View all matches</a>
    <a href="${pageContext.request.contextPath}/">🎾 Start new match</a>
</div>

</body>
</html>