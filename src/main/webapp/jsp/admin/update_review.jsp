<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.make_hide" var="make_hide"/>
<fmt:message key="button.make_show" var="make_show"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.not_data" var="not_data"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="review.author" var="author_table"/>
<fmt:message key="review.content" var="content_table"/>
<fmt:message key="review.date" var="date_table"/>
<fmt:message key="review.id" var="id_table"/>
<fmt:message key="review.room_mark" var="room_mark_table"/>
<fmt:message key="title.update_review" var="title"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${title}
    </title>
</head>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<body>
<div class="container text-secondary">
    <div class="mb-3 fw-bold">
        ${title}
    </div>
    <c:choose>
        <c:when test="${not empty wrong_review_id_ses}">
            ${not_found}
        </c:when>
        <c:when test="${not empty update_review_result}">
            ${update_review_result eq true? complete: failed}
        </c:when>
        <c:when test="${empty review_ses}">
            ${not_data}
        </c:when>
        <c:otherwise>
            <table class="table text-secondary border-secondary">
                <thead>
                <tr>
                    <th scope="col">${title}</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${id_table}</td>
                    <td>${review_ses.entityId}</td>
                </tr>
                <tr>
                    <td>${date_table}</td>
                    <td>${review_ses.date}</td>
                </tr>
                <tr>
                    <td>${room_mark_table}</td>
                    <td>${review_ses.roomMark}</td>
                </tr>
                <tr>
                    <td>${content_table}</td>
                    <td>${review_ses.reviewContent}</td>
                </tr>
                <tr>
                    <td>${author_table}</td>
                    <td>${review_ses.author}</td>
                </tr>
                </tbody>
            </table>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_review"/>
                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary text-center">
                            ${review_ses.hidden eq true? make_show: make_hide}
                    </button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
