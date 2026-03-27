<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>Tennis Scoreboard</h1>

<div class="scoreboard">
    <a class="start-new-match" href="${pageContext.request.contextPath}/new-match">Start a new match</a>
    <a class="match-results" href="${pageContext.request.contextPath}/matches">View match results</a>
    <a class="sample-matches" href="${pageContext.request.contextPath}/sample-matches">Add sample matches</a>
</div>

</body>
</html>