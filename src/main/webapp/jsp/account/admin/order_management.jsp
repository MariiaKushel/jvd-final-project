<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.order_management" var="title"/>
<fmt:message key="reference.create_new" var="create_new"/>
<fmt:message key="reference.find_all" var="find_all"/>
<fmt:message key="button.find" var="find"/>
<fmt:message key="reference.update" var="update"/>
<fmt:message key="order.id" var="id_table"/>
<fmt:message key="order.user_id" var="user_id_table"/>
<fmt:message key="order.room_id" var="room_id_table"/>
<fmt:message key="order.date" var="date_table"/>
<fmt:message key="order.check_in" var="check_in_table"/>
<fmt:message key="order.check_out" var="check_out_table"/>
<fmt:message key="order.amount" var="amount_table"/>
<fmt:message key="order.status" var="status_table"/>
<fmt:message key="order.prepayment" var="prepayment_table"/>
<fmt:message key="field.order_status" var="status"/>
<fmt:message key="field.prepayment" var="order_prepayment"/>
<fmt:message key="field.date_from" var="from"/>
<fmt:message key="field.date_to" var="to"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="message.not_found" var="not_found"/>

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
                    <input type="hidden" name="command" value="find_all_orders"/></br>
                    <button type="submit" class="btn btn-light">
                        ${find_all}
                    </button>
                </form>
            </div>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_order_by_status"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${status}
                    </label>
                    <div class="row">
                        <div class="col">
                            <select class="form-select" name="order_status">
                                <option value="" label="" selected></option>
                                <c:forEach var="cur_status" items="${all_order_statuses_ses}">
                                    <option value="${cur_status}"
                                        ${search_parameter_atr['order_status_atr'] eq cur_status? 'selected': ' '}>
                                            ${cur_status}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
            </form>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_order_by_prepayment"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${order_prepayment}
                    </label>
                    <div class="row">
                        <div class="col">
                            <select class="form-select" name="prepayment">
                                <option value="" label="" selected></option>
                                <c:forEach var="cur_prepayment" items="${prepayment_ses}">
                                    <option value="${cur_prepayment}"
                                        ${not empty search_parameter_atr['prepayment_atr'] and search_parameter_atr['prepayment_atr'] eq cur_prepayment? 'selected': ' '}>
                                            ${cur_prepayment}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
            </form>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_order_by_date_range"/>
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
                            <input type="date" max=${today} name="date_to"
                                   value="${search_parameter_atr['date_to_atr']}"
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
                            <th scope="col">${user_id_table}</th>
                            <th scope="col">${room_id_table}</th>
                            <th scope="col">${date_table}</th>
                            <th scope="col">${check_in_table}</th>
                            <th scope="col">${check_out_table}</th>
                            <th scope="col">${status_table}</th>
                            <th scope="col">${prepayment_table}</th>
                            <th scope="col">${update}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="element" items="${order_list_atr}">
                            <tr>
                                <td>${element.entityId}</td>
                                <td>${element.userId}</td>
                                <td>${element.roomId}</td>
                                <td>${element.date}</td>
                                <td>${element.from}</td>
                                <td>${element.to}</td>
                                <td>${element.status}</td>
                                <td>${element.prepayment}</td>
                                <c:choose>
                                    <c:when test="${can_be_updated_map_atr[element.entityId] eq true}">
                                        <td><a class="text-secondary text-decoration-none"
                                               href="${path}/controller?command=go_to_update_order_page&order_id=${element.entityId}">
                                                ${update}</a></td>
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
