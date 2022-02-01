<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="field.prepayment" var="prepay"/>
<fmt:message key="message.base_amount" var="amount_without_discount"/>
<fmt:message key="message.check_in" var="check_in"/>
<fmt:message key="message.check_out" var="check_out"/>
<fmt:message key="message.check_out" var="check_out"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="message.days" var="days_to_stay"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.total_amount" var="amount_with_discount"/>
<fmt:message key="message.your_discount" var="your_discount"/>
<fmt:message key="room.number" var="number"/>
<fmt:message key="room.price" var="room_price"/>
<fmt:message key="title.order" var="title"/>

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
        <c:when test="${not empty order_result}">
            ${order_result eq true? complete: failed}
        </c:when>
        <c:when test="${not empty order_data_ses['not_found_ses']}">
            ${not_found}
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
                    <td>${number}</td>
                    <td>${order_data_ses['room_number_ses']}</td>
                </tr>
                <tr>
                    <td>${check_in}</td>
                    <td>${order_data_ses['date_from_ses']}</td>
                </tr>
                <tr>
                    <td>${check_out}</td>
                    <td>${order_data_ses['date_to_ses']}</td>
                </tr>
                <tr>
                    <td>${days_to_stay}</td>
                    <td>${order_data_ses['days_ses']}</td>
                </tr>
                <tr>
                    <td>${room_price}</td>
                    <td>${order_data_ses['price_ses']}</td>
                </tr>
                <tr>
                    <td>${amount_without_discount}</td>
                    <td>${order_data_ses['base_amount_ses']}</td>
                </tr>
                <tr>
                    <td>${amount_with_discount}</td>
                    <td>${order_data_ses['total_amount_ses']}
                        (${your_discount} ${order_data_ses['rate_ses']} %)</td>
                </tr>
                </tbody>
            </table>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="create_order"/>
                <input type="hidden" name="date_from" value="${order_data_ses['date_from_ses']}"/>
                <input type="hidden" name="date_to" value="${order_data_ses['date_to_ses']}"/>
                <input type="hidden" name="room_id" value="${order_data_ses['room_id_ses']}"/>
                <input type="hidden" name="total_amount" value="${order_data_ses['total_amount_ses']}"/>
                <input type="hidden" name="days" value="${order_data_ses['days_ses']}"/>

                <div class="row border-secondary">
                    <div class="col mb-3 text-center">
                        <div class="form-check form-check-inline">
                            <input type="checkbox" class="form-check-input " name="prepayment" value="true"/>
                            <label class="form-check-label">${prepay}</label>
                        </div>
                    </div>
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
