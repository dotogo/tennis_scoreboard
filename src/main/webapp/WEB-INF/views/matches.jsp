<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/matches-style.css">
</head>
<body>
<c:set var="baseUrl" value="${pageContext.request.contextPath}/matches" />

<h1>Matches</h1>

<form action="${baseUrl}" method="get">
    <div class="player-selection">
        <input type="text" name="filter_by_player_name" value="<c:out value='${param.filter_by_player_name}'/>" class="filter-field" >
        <input type="hidden" name="page" value="1">
        <button type="submit" class="btn btn-primary">
            🔍 Search
        </button>
        <a href="${baseUrl}" class="btn btn-secondary">✖ Reset</a>
    </div>
</form>

<c:choose>
    <c:when test="${empty matches}">
        <p class="no-matches">No matches found.</p>
    </c:when>

    <c:otherwise>
        <table class="matches-table">
            <thead>
            <tr>
                <th>First Player</th>
                <th>Second Player</th>
                <th>Winner</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="match" items="${matches.items}" varStatus="status">
                <tr>
                    <td>${match.firstPlayerName}</td>
                    <td>${match.secondPlayerName}</td>
                    <td class="winner-name">
                            <span class="winner-badge ${status.index % 2 == 0 ? 'odd' : 'even'}">
                                    ${match.winnerName}
                            </span>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%-- Pagination --%>
        <nav class="pagination">

                <%-- Previous --%>
            <c:choose>
                <c:when test="${matches.hasPreviousPage}">
                    <a href="${baseUrl}?page=${matches.currentPage - 1}&filter_by_player_name=<c:out value='${filter_by_player_name}'/>">
                        &laquo;
                    </a>
                </c:when>
                <c:otherwise>
                    <span class="page-disabled">&laquo;</span>
                </c:otherwise>
            </c:choose>

                        <%-- Page numbers --%>
                    <c:forEach var="i" items="${pageRange}">
                        <c:choose>
                            <c:when test="${i == matches.currentPage}">
                                <span class="page-current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="${baseUrl}?page=${i}&filter_by_player_name=<c:out value='${filter_by_player_name}'/>">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                <%-- Next --%>
            <c:choose>
                <c:when test="${matches.hasNextPage}">
                    <a href="${baseUrl}?page=${matches.currentPage + 1}&filter_by_player_name=<c:out value='${filter_by_player_name}'/>">
                        &raquo;
                    </a>
                </c:when>
                <c:otherwise>
                    <span class="page-disabled">&raquo;</span>
                </c:otherwise>
            </c:choose>

        </nav>

        <p class="pagination-info">
            Page ${matches.currentPage} of ${matches.totalPages}
            (total: ${matches.totalItems})
        </p>

    </c:otherwise>
</c:choose>

<div class="nav-links">
    <a href="${pageContext.request.contextPath}/">🎾 Start new match</a>
</div>

</body>
</html>