<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>Welcome to Tennis Scoreboard</h1>

<div class="scoreboard">
    <h3>Manage your tennis matches, record results, and track rankings</h3>
</div>


<div class="main-image">
    <img src="${pageContext.request.contextPath}/images/tennis-main.jpg"
         alt="">
</div>

<div class="scoreboard">
    <a class="start-new-match" href="${pageContext.request.contextPath}/new-match">Start a new match</a>
    <a class="match-results" href="${pageContext.request.contextPath}/matches">View match results</a>
</div>

<div class="sample-matches-link">
    <c:choose>
        <c:when test="${sampleMatchesAvailable.get()}">
            <a class="sample-matches" href="${pageContext.request.contextPath}/sample-matches">Add sample matches</a>
        </c:when>

        <c:otherwise>
            <a class="sample-matches" href="${pageContext.request.contextPath}/">50 sample matches added</a>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>