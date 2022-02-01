<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="field.room_number" var="room_number_table"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="message.content_rules" var="content_rules"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.not_data" var="not_data"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="order.amount" var="amount_table"/>
<fmt:message key="order.check_in" var="check_in_table"/>
<fmt:message key="order.check_out" var="check_out_table"/>
<fmt:message key="order.date" var="date_table"/>
<fmt:message key="order.id" var="order_id_table"/>
<fmt:message key="order.prepayment" var="prepayment_table"/>
<fmt:message key="review.content" var="review_text"/>
<fmt:message key="review.room_mark" var="room_mark"/>
<fmt:message key="room.sleeping_place" var="sleeping_place_table"/>
<fmt:message key="title.create_review" var="title"/>

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
        <c:when test="${not empty wrong_order_id}">
            ${not_found}
        </c:when>
        <c:when test="${not empty create_review_result}">
            ${create_review_result eq true? complete: failed}
        </c:when>
        <c:when test="${empty order_ses}">
            ${not_found}
        </c:when>
        <c:when test="${empty review_data_ses}">
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
                    <td>${order_id_table}</td>
                    <td>${order_ses.entityId}</td>
                </tr>
                <tr>
                    <td>${room_number_table}</td>
                    <td>${room_ses.number}</td>
                </tr>
                <tr>
                    <td>${sleeping_place_table}</td>
                    <td>${room_ses.sleepingPlace}</td>
                </tr>
                <tr>
                    <td>${check_in_table}</td>
                    <td>${order_ses.from}</td>
                </tr>
                <tr>
                    <td>${check_out_table}</td>
                    <td>${order_ses.to}</td>
                </tr>
                <tr>
                    <td>${amount_table}</td>
                    <td>${order_ses.amount}</td>
                </tr>
                <tr>
                    <td>${prepayment_table}</td>
                    <td>${order_ses.prepayment}</td>
                </tr>
                </tbody>
            </table>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="create_review"/>
                <div class="row">
                    <div class="col-auto col-form-label mb-3">
                        <label class="form-label">
                                ${room_mark}
                        </label>
                    </div>
                    <div class="col col-form-label mb-3">
                        <select class="form-select" name="mark">
                            <option value="" selected></option>
                            <c:forEach var="cur_mark" items="${marks_ses}">
                                <option value="${cur_mark}">${cur_mark}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                            ${review_text}
                    </label>
                    <textarea maxlength="500" name="content"
                              value="${review_data_ses['content_ses']}"
                              class="form-control" style="height: 100px"></textarea>
                    <label class="form-label text-danger">
                        <c:if test="${not empty review_data_ses['wrong_content_ses']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>
                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary text-center">
                            ${confirm}
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
