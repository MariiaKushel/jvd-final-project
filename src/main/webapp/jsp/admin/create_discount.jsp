<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="discount.id" var="discount_id"/>
<fmt:message key="discount.rate" var="discount_rate"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="message.discount_rules" var="discount_rules"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="title.create_discount" var="title"/>

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
        <c:when test="${not empty create_discount_result}">
            ${create_discount_result eq true? complete: failed}
        </c:when>
        <c:otherwise>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="create_discount"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${discount_rate}
                    </label>
                    <div class="col-md-10">
                        <input type="number"  min="0" max ="100" name="rate" value="${discount_data_ses['rate_ses']}"
                               required oninvalid="this.setCustomValidity('${discount_rules}')"
                               class="form-control">
                    </div>
                    <div class="row">
                        <div class="col text-danger">
                            <c:if test="${not empty discount_data_ses['wrong_rate_ses']}">
                                ${incorrect_data}
                            </c:if>
                        </div>
                    </div>
                    <div class="container text-center">
                        <button type="submit" class="btn btn-secondary">
                                ${confirm}
                        </button>
                    </div>
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
