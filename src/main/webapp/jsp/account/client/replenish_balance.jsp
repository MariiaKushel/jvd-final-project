<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.replenish_balance" var="title"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="field.balance" var="balance"/>
<fmt:message key="field.amount" var="amount"/>
<fmt:message key="message.balance_rules" var="balance_rules"/>
<fmt:message key="message.incorrect_amount" var="incorrect_amount"/>
<fmt:message key="message.incorrect_amount_oversize" var="incorrect_amount_oversize"/>

<fmt:message key="reference.show_password" var="show_password"/>
<fmt:message key="reference.hide_password" var="hide_password"/>
<fmt:message key="field.new_password" var="new_password"/>
<fmt:message key="field.old_password" var="old_password"/>
<fmt:message key="message.incorrect_password" var="incorrect_password"/>
<fmt:message key="button.update" var="update"/>
<fmt:message key="message.error_password" var="error_password"/>
<fmt:message key="message.not_data" var="not_data"/>

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
<div class="container text-secondary ">
    <div class="mb-3 fw-bold">
        ${title}
    </div>
    <c:choose>
        <c:when test="${not empty not_found_ses}">
            ${not_found}
        </c:when>
        <c:when test="${not empty replenish_balance_result}">
            ${replenish_balance_result eq true? complete: failed}
        </c:when>
        <c:when test="${empty balance_data_ses}">
            ${not_data}
        </c:when>
        <c:otherwise>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="replenish_balance"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${balance}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${balance_data_ses['balance_ses']}" class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${amount}
                    </label>
                    <div class="col-md-10">
                        <input type="number" step="0.01" min="0" max="9999999.99" name="replenish_amount"
                               required oninvalid="this.setCustomValidity('${balance_rules}')"
                               class="form-control">
                    </div>
                    <div class="row">
                        <div class="col text-danger">
                            <c:if test="${not empty balance_data_ses['wrong_amount_ses']}">
                                ${incorrect_amount}
                            </c:if>
                            <c:if test="${not empty balance_data_ses['wrong_amount_oversize_ses']}">
                                ${incorrect_amount_oversize}//
                            </c:if>
                        </div>
                    </div>
                    <div class="container text-center">
                        <button type="submit" class="btn btn-secondary">
                                ${update}
                        </button>
                    </div>
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
