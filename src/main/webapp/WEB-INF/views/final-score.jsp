<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Final score</title>
    <style>
        .nav-links {
            margin-top: 30px;
            padding: 20px;
            border-top: 1px solid #ccc;
        }
        .nav-links a {
            display: inline-block;
            margin-right: 20px;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .nav-links a:hover {
            background-color: #45a049;
        }
    </style>
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