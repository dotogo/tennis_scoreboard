<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
<h1>New Match</h1>

<form action="/new-match" method="post">
    <div class="form-group">
        <label for="first-player">Player 1 Name:</label>
        <input type="text" id="first-player" name="first-player" required>
    </div>

    <div class="form-group">
        <label for="second-player">Player 2 Name:</label>
        <input type="text" id="second-player" name="second-player" required>
    </div>

    <button type="submit" class="btn-submit">Start Match</button>
</form>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>