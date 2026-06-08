<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Start new match</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h3>
    <a href="${pageContext.request.contextPath}/">
        Tennis Scoreboard
    </a>
</h3>
<h1>New Match</h1>

<div class="match-form">
    <form action="${pageContext.request.contextPath}/new-match" method="post">
        <div class="form-group">
            <label for="first-player">Player 1 Name:</label>
            <input type="text" id="first-player" name="first-player" required>

            <div class="player-image">
                <img src="${pageContext.request.contextPath}/images/first-player-img.jpg"
                     alt="">
            </div>
        </div>

        <div class="form-group">
            <label for="second-player">Player 2 Name:</label>
            <input type="text" id="second-player" name="second-player" required>

            <div class="player-image">
                <img src="${pageContext.request.contextPath}/images/second-player-img.jpg"
                     alt="">
            </div>
        </div>

        <button type="submit" class="btn-submit">Start Match</button>
    </form>
</div>


<c:if test="${not empty error_message}">
    <div class="error-message" style="white-space: pre-line;">
        <c:out value="${error_message}" />
    </div>
</c:if>

<div class="nav-links">
    <a href="${pageContext.request.contextPath}/matches">📋 View all matches</a>
</div>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
