<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.orders" var="title"/>

<fmt:message key="reference.find_all" var="find_all"/>
<fmt:message key="button.find" var="find"/>
<fmt:message key="reference.cancel" var="cancel_table"/>

<fmt:message key="order.id" var="id_table"/>
<fmt:message key="order.date" var="date_table"/>
<fmt:message key="order.check_in" var="check_in_table"/>
<fmt:message key="order.check_out" var="check_out_table"/>
<fmt:message key="order.amount" var="amount_table"/>
<fmt:message key="order.status" var="status_table"/>
<fmt:message key="order.prepayment" var="prepayment_table"/>
<fmt:message key="order.review" var="review_table"/>
<fmt:message key="field.order_status" var="status"/>
<fmt:message key="field.prepayment" var="order_prepayment"/>
<fmt:message key="field.date_from" var="from"/>
<fmt:message key="field.date_to" var="to"/>
<fmt:message key="field.last" var="find_last"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="reference.create_new" var="create_new"/>
<fmt:message key="message.review_done" var="review_done"/>
<fmt:message key="message.number_rules" var="number_rules"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>

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
    <div class="fw-bold mb-3">
        ${title}
    </div>
    <div class="row">
        <div class="col-sm-3 mb-3 bg-secondary opacity-75 text-white">
            <div class="mb-3">
                <form method="get" action="${path}/controller">
                    <input type="hidden" name="command" value="find_order_by_user_id"/></br>
                    <button type="submit" class="btn btn-light">
                        ${find_all}
                    </button>
                </form>
            </div>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_order_by_user_id_last"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${find_last}
                    </label>
                    <div class="row">
                        <div class="col">
                            <input type="number" min="1" max="99999" name="last" pattern="//d{1,5}"
                                   value="${search_parameter_atr['last_atr']}"
                                   oninvalid="this.setCustomValidity('${number_rules}')"
                                   class="form-control"/>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                    <label class="form-label text-dark">
                        <c:if test="${not empty search_parameter_atr['wrong_last_atr']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>
            </form>

        </div>
        <div class="col-sm-9 mb-3">
            <c:choose>
                <c:when test="${not empty new_search_atr and empty order_list_atr}">
                    ${new_search}
                </c:when>
                <c:when test="${empty new_search_atr and empty order_list_atr}">
                    ${not_found}
                </c:when>
                <c:otherwise>
                    <table class="table text-secondary border-secondary">
                        <thead>
                        <tr>
                            <th scope="col">${id_table}</th>
                            <th scope="col">${date_table}</th>
                            <th scope="col">${check_in_table}</th>
                            <th scope="col">${check_out_table}</th>
                            <th scope="col">${status_table}</th>
                            <th scope="col">${prepayment_table}</th>
                            <th scope="col">${cancel_table}</th>
                            <th scope="col">${review_table}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="element" items="${order_list_atr}">
                            <tr>
                                <td>${element.entityId}</td>
                                <td>${element.date}</td>
                                <td>${element.from}</td>
                                <td>${element.to}</td>
                                <td>${element.status}</td>
                                <td>${element.prepayment}</td>

                                <c:choose>
                                    <c:when test="${can_be_canceled_map_atr[element.entityId]}">
                                        <td><a class="text-secondary text-decoration-none"
                                               href="${path}/controller?command=go_to_cancel_order_page&order_id=${element.entityId}">
                                                ${cancel_table}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>

                                <c:choose>
                                    <c:when test="${review_map_atr[element.entityId] eq true}">
                                        <td>${review_done}</td>
                                    </c:when>
                                    <c:when test="${empty review_map_atr[element.entityId] eq false}">
                                        <td><a class="text-secondary text-decoration-none"
                                               href="${path}/controller?command=go_to_create_review_page&order_id=${element.entityId}">
                                                ${create_new}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td></td>
                                    </c:otherwise>
                                </c:choose>
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
    <jsp:include page="../../footer/footer.jsp"/>
</footer>
</body>
</html>
