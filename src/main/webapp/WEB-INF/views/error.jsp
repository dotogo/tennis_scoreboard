<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Error page</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
<h3>
  <a href="${pageContext.request.contextPath}/">
    Tennis Scoreboard
  </a>
</h3>

<div class="error-container">
  <h1>⚠️ Error</h1>

  <div class="error-image">
    <img src="${pageContext.request.contextPath}/images/oops.jpg"
         alt="Error icon">
  </div>

  <c:choose>
    <c:when test="${empty error_message}">
      <p class="error-message">An unexpected error occurred.</p>
    </c:when>

    <c:otherwise>
      <div class="error-message" style="white-space: pre-line;">
        <c:out value="${error_message}" />
      </div>
    </c:otherwise>
  </c:choose>

  <h3>
    <a href="${pageContext.request.contextPath}/" class="back-link">
      ← Back to Home
    </a>
  </h3>

</div>

</body>
</html>
