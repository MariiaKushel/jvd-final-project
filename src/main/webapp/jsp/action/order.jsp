<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.order" var="title"/>
<fmt:message key="message.room_number" var="number"/>
<fmt:message key="message.room_price" var="room_price"/>
<fmt:message key="message.check_in" var="check_in"/>
<fmt:message key="message.check_out" var="check_out"/>
<fmt:message key="message.check_out" var="check_out"/>
<fmt:message key="message.days" var="days_to_stay"/>
<fmt:message key="message.total_amount" var="amount_with_discount"/>
<fmt:message key="message.base_amount" var="amount_without_discount"/>
<fmt:message key="message.your_discount" var="your_discount"/>
<fmt:message key="message.thank" var="thank"/>
<fmt:message key="message.order_failed" var="order_failed"/>
<fmt:message key="button.confirm" var="confirm"/>

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
    <c:choose>
        <c:when test="${not empty order_result}">
            ${order_result eq true? thank: order_failed}
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
                    <td>${room_number_atr}</td>
                </tr>
                <tr>
                    <td>${check_in}</td>
                    <td>${date_from_atr}</td>
                </tr>
                <tr>
                    <td>${check_out}</td>
                    <td>${date_to_atr}</td>
                </tr>
                <tr>
                    <td>${days_to_stay}</td>
                    <td>${days_atr}</td>
                </tr>
                <tr>
                    <td>${room_price}</td>
                    <td>${room_price_atr}</td>
                </tr>
                <tr>
                    <td>${amount_without_discount}</td>
                    <td>${base_amount_atr}</td>
                </tr>
                <tr>
                    <td>${amount_with_discount}</td>
                    <td>${total_amount_atr} (${your_discount} ${base_amount_atr-total_amount_atr})</td>
                </tr>
                </tbody>
            </table>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="create_order"/>
                <input type="hidden" name="date_from" value="${date_from_atr}"/>
                <input type="hidden" name="date_to" value="${date_to_atr}"/>
                <input type="hidden" name="room_id" value="${room_id_atr}"/>
                <input type="hidden" name="total_amount" value="${total_amount_atr}"/>
                <input type="hidden" name="days" value="${days_atr}"/>

                <div class="row border-secondary">
                    <div class="col mb-3 text-center">
                        <div class="form-check form-check-inline">
                            <input type="checkbox" class="form-check-input " name="prepayment" value="true"/>
                            <label class="form-check-label">Prepayment</label>
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
