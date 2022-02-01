<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.update" var="update"/>
<fmt:message key="field.balance" var="user_balance"/>
<fmt:message key="field.discount" var="user_discount"/>
<fmt:message key="field.email" var="user_email"/>
<fmt:message key="field.name" var="user_name"/>
<fmt:message key="field.phone_number" var="user_phone_number"/>
<fmt:message key="field.role" var="user_role"/>
<fmt:message key="field.user_status" var="user_status_name"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="reference.hide_password" var="hide_password"/>
<fmt:message key="reference.show_password" var="show_password"/>
<fmt:message key="title.account" var="title"/>

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
        <c:when test="${not empty not_found_ses}">
            ${not_found}
        </c:when>
        <c:when test="${not empty update_personal_data_result}">
            ${complete}
        </c:when>
        <%--<c:when test="${empty user_data_ses['phone_email_ses']}">
            ${not_found}
        </c:when>--%>
        <c:otherwise>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="update_personal_data"/>
                <input type="hidden" name="phone_number" value="${user_data_ses['phone_number_ses']}">
                <input type="hidden" name="name" value="${user_data_ses['name_ses']}">

                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_email}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['email_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_name}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['name_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_phone_number}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['phone_number_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_role}
                    </label>
                    <div class="col-md-10">
                        <select class="form-select" name="role">
                            <option value="${user_data_ses['role_ses']}" selected>${user_data_ses['role_ses']}</option>
                            <c:forEach var="cur_role" items="${all_user_roles_ses}">
                                <c:if test="${user_data_ses['role_ses']!=cur_role}">
                                    <option value="${cur_role}" }>${cur_role}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_status_name}
                    </label>
                    <div class="col-md-10">
                        <select class="form-select" name="user_status">
                            <option value="${user_data_ses['user_status_ses']}"
                                    selected>${user_data_ses['user_status_ses']}</option>
                            <c:forEach var="cur_status" items="${all_user_statuses_ses}">
                                <c:if test="${user_data_ses['user_status_ses']!=cur_status}">
                                    <option value="${cur_status}" }>${cur_status}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_balance}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${user_data_ses['balance_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_discount}
                    </label>
                    <div class="col-md-9">
                        <select class="form-select" name="discount_id">
                            <option value="${user_data_ses['discount_id_ses']}"
                                    selected>${user_data_ses['rate_ses']}</option>
                            <c:forEach var="cur_discount" items="${all_discounts_ses}">
                                <c:if test="${user_data_ses['discount_id_ses']!=cur_discount.entityId}">
                                    <option value="${cur_discount.entityId}" }>${cur_discount.rate}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-sm-1 mb-3">
                        %
                    </div>
                </div>
                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary">
                            ${update}
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
