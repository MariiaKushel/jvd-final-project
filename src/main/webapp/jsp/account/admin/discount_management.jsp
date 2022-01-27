<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.discount_management" var="title"/>
<fmt:message key="reference.create_new" var="create_new"/>
<fmt:message key="reference.find_all" var="find_all"/>
<fmt:message key="button.find" var="find"/>
<fmt:message key="reference.update" var="cancel_table"/>
<fmt:message key="reference.remove" var="remove_table"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.number_rules" var="number_rules"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="field.rate" var="discount_rate"/>

<fmt:message key="discount.id" var="id_table"/>
<fmt:message key="discount.rate" var="rate_table"/>

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
                <a class="link-light text-decoration-none" href="${path}/controller?command=#">${create_new}</a>
            </div>
            <div class="mb-3">
                <a class="link-light text-decoration-none"
                   href="${path}/controller?command=find_all_discounts">${find_all}</a>
            </div>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_discount_by_rate"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${discount_rate}
                    </label>
                    <div class="row">
                        <div class="col">
                            <input type="number" min="1" max="99999" name="rate" pattern="//d{1,5}"
                                   value="${search_parameter_atr['rate_atr']}"
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
                        <c:if test="${not empty search_parameter_atr['wrong_rate_atr']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>
            </form>
        </div>
        <div class="col-sm-9 mb-3">
            <c:choose>
                <c:when test="${not empty new_search_atr and empty discount_list_atr}">
                    ${new_search}
                </c:when>
                <c:when test="${empty new_search_atr and empty discount_list_atr}">
                    ${not_found}
                </c:when>
                <c:otherwise>
                    <table class="table text-secondary border-secondary">
                        <thead>
                        <tr>
                            <th scope="col">${id_table}</th>
                            <th scope="col">${rate_table}</th>
                            <th scope="col">${cancel_table}</th>
                            <th scope="col">${remove_table}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="element" items="${discount_list_atr}">
                            <tr>
                                <td>${element.entityId}</td>
                                <td>${element.rate}</td>
                                <td><a class="text-secondary text-decoration-none"
                                       href="${path}/controller?command=#&discount_id=${element.entityId}">
                                        ${cancel_table}</a></td>
                                <td><a class="text-secondary text-decoration-none"
                                       href="${path}/controller?command=#&discount_id=${element.entityId}">
                                        ${remove_table}</a></td>
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
