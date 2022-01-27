<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.update_order" var="title"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="order.id" var="order_id_table"/>
<fmt:message key="order.date" var="date_table"/>
<fmt:message key="order.check_in" var="check_in_table"/>
<fmt:message key="order.check_out" var="check_out_table"/>
<fmt:message key="order.amount" var="amount_table"/>
<fmt:message key="order.prepayment" var="prepayment_table"/>
<fmt:message key="order.status" var="status_table"/>
<fmt:message key="field.room_number" var="room_number_table"/>
<fmt:message key="room.sleeping_place" var="sleeping_place_table"/>
<fmt:message key="button.update" var="update"/>

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
    <jsp:include page="../../header/header.jsp"/>
</header>
<body>
<div class="container text-secondary">
    <div class="mb-3 fw-bold">
        ${title}
    </div>
    <c:choose>
        <c:when test="${not empty search_parameter_atr['wrong_order_id']}">
            ${not_found}
        </c:when>
        <c:when test="${not empty update_order_result}">
            ${update_order_result eq true? complete: failed}
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
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="update_order"/>
                <div class="mb-3">
                    <label class="form-label">
                            ${status_table}
                    </label>
                    <select class="form-select" name="order_status">
                        <option value="${order_ses.status}" label="${order_ses.status}" selected></option>
                        <c:forEach var="cur_status" items="${available_order_statuses_ses}">
                            <option value="${cur_status}">
                                    ${cur_status}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary text-center">
                            ${update}
                    </button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<footer>
    <jsp:include page="../../footer/footer.jsp"/>
</footer>
</body>
</html>
