<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.find" var="find"/>
<fmt:message key="field.all_statuses" var="all_statuses"/>
<fmt:message key="field.email" var="email_search"/>
<fmt:message key="field.name" var="name_search"/>
<fmt:message key="field.phone_number" var="phone_number_search"/>
<fmt:message key="field.phone_number" var="phone_number_search"/>
<fmt:message key="field.user_status" var="user_status_search"/>
<fmt:message key="message.email_rules" var="email_rules"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.name_rules" var="name_rules"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.phone_number_rules" var="phone_number_rules"/>
<fmt:message key="reference.update" var="cancel_table"/>
<fmt:message key="title.user_management" var="title"/>
<fmt:message key="user.balance" var="balanse_table"/>
<fmt:message key="user.discount_id" var="discount_id_table"/>
<fmt:message key="user.email" var="email_table"/>
<fmt:message key="user.id" var="id_table"/>
<fmt:message key="user.name" var="name_table"/>
<fmt:message key="user.phone_number" var="phone_number_table"/>
<fmt:message key="user.role" var="role_table"/>
<fmt:message key="user.status" var="status_table"/>

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
            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_user_by_parameter"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${email_search}
                    </label>
                    <input type="text" maxlength="50" name="email" pattern="[\da-z_\-\.@]+"
                           value="${search_parameter_atr['email_atr']}"
                           oninvalid="this.setCustomValidity('${email_rules}')"
                           class="form-control"/>
                    <label class="form-label text-dark">
                        <c:if test="${not empty search_parameter_atr['wrong_email_atr']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${phone_number_search}
                    </label>
                    <input type="text" maxlength="13" name="phone_number" pattern="[\+\d]{1,13}"
                           value="${search_parameter_atr['phone_number_atr']}"
                           oninvalid="this.setCustomValidity('${phone_number_rules}')"
                           class="form-control"/>
                    <label class="form-label text-dark">
                        <c:if test="${not empty search_parameter_atr['wrong_phone_number_atr']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${name_search}
                    </label>
                    <input type="text" maxlength="50" name="name" pattern="[\wа-яА-яёЁ][\wа-яА-яёЁ\s]*"
                           value="${search_parameter_atr['name_atr']}"
                           oninvalid="this.setCustomValidity('${name_rules}')"
                           class="form-control"/>
                    <label class="form-label text-dark">
                        <c:if test="${not empty search_parameter_atr['wrong_name_atr']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${user_status_search}
                    </label>
                    <select class="form-select" name="user_status">
                        <option value="" selected>${all_statuses}</option>
                        <c:forEach var="cur_status" items="${all_user_statuses_ses}">
                            <option value="${cur_status}"
                                ${search_parameter_atr['user_status_atr'] eq cur_status? 'selected': ' '}>
                                    ${cur_status}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-light">
                    ${find}
                </button>
            </form>
        </div>
        <div class="col-sm-9 mb-3">
            <c:choose>
                <c:when test="${not empty new_search_atr and empty user_list_atr}">
                    ${new_search}
                </c:when>
                <c:when test="${empty new_search_atr and empty user_list_atr}">
                    ${not_found}
                </c:when>
                <c:otherwise>
                    <table class="table text-secondary border-secondary">
                        <thead>
                        <tr>
                            <th scope="col">${id_table}</th>
                            <th scope="col">${email_table}</th>
                            <th scope="col">${phone_number_table}</th>
                            <th scope="col">${name_table}</th>
                            <th scope="col">${status_table}</th>
                            <th scope="col">${role_table}</th>
                            <th scope="col">${discount_id_table}</th>
                            <th scope="col">${balanse_table}</th>
                            <th scope="col">${cancel_table}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="element" items="${user_list_atr}">
                            <tr>
                                <td>${element.entityId}</td>
                                <td>${element.email}</td>
                                <td>${element.phoneNumber}</td>
                                <td>${element.name}</td>
                                <td>${element.status}</td>
                                <td>${element.role}</td>
                                <td><c:forEach var="dis" items="${all_discounts_ses}">
                                    <c:if test="${dis.entityId==element.discountId}">
                                        ${dis.rate}
                                    </c:if>
                                </c:forEach>
                                </td>
                                <td>${element.balance}</td>
                                <td><a class="text-secondary text-decoration-none"
                                       href="${path}/controller?command=go_to_account_page&user_id=${element.entityId}">
                                        ${cancel_table}</a></td>
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
