
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
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

</body>
</html>
