<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.find" var="find"/>
<fmt:message key="field.date_from" var="from"/>
<fmt:message key="field.date_to" var="to"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="reference.create_new" var="create_new"/>
<fmt:message key="reference.find_all" var="find_all"/>
<fmt:message key="reference.update" var="update_table"/>
<fmt:message key="review.author" var="author_table"/>
<fmt:message key="review.date" var="date_table"/>
<fmt:message key="review.hidden" var="hidden_table"/>
<fmt:message key="review.id" var="id_table"/>
<fmt:message key="review.room_mark" var="mark_table"/>
<fmt:message key="title.review_management" var="title"/>

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
    <div class="fw-bold mb-3">
        ${title}
    </div>
    <div class="row">
        <div class="col-sm-3 mb-3 bg-secondary opacity-75 text-white">
            <div class="mb-3">
                <form method="get" action="${path}/controller">
                    <input type="hidden" name="command" value="find_all_reviews"/></br>
                    <button type="submit" class="btn btn-light">
                        ${find_all}
                    </button>
                </form>
            </div>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_review_by_date_range"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${from}
                    </label>
                    <input type="date" max=${today} name="date_from" value="${search_parameter_atr['date_from_atr']}"
                           class="form-control">
                    <label class="form-label">
                        ${to}
                    </label>
                    <div class="row">
                        <div class="col">
                            <input type="date" max=${today} name="date_to" value="${search_parameter_atr['date_to_atr']}"
                                   class="form-control">
                            <label class="form-label text-dark">
                                <c:if test="${not empty search_parameter_atr['wrong_date_range_atr']}">
                                    ${incorrect_data}
                                </c:if>
                            </label>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
            </form>


        </div>
        <div class="col-sm-9 mb-3">
            <c:choose>
                <c:when test="${not empty new_search_atr and empty review_list_atr}">
                    ${new_search}
                </c:when>
                <c:when test="${empty new_search_atr and empty review_list_atr}">
                    ${not_found}
                </c:when>
                <c:otherwise>
                    <table class="table text-secondary border-secondary">
                        <thead>
                        <tr>
                            <th scope="col">${id_table}</th>
                            <th scope="col">${date_table}</th>
                            <th scope="col">${mark_table}</th>
                            <th scope="col">${hidden_table}</th>
                            <th scope="col">${author_table}</th>
                            <th scope="col">${update_table}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="element" items="${review_list_atr}">
                            <tr>
                                <td>${element.entityId}</td>
                                <td>${element.date}</td>
                                <td>${element.roomMark}</td>
                                <td>${element.hidden}</td>
                                <td>${element.author}</td>
                                <td><a class="text-secondary text-decoration-none"
                                       href="${path}/controller?command=go_to_update_review_page&review_id=${element.entityId}">
                                        ${update_table}</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
