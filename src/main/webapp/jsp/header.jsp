<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.header" var="title"/>
<fmt:message key="message.name" var="name"/>
<fmt:message key="message.phone" var="phone"/>
<fmt:message key="language.ru" var="ru"/>
<fmt:message key="language.en" var="en"/>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${title}
    </title>
</head>
<body>
<div class="container p-3 mb-2 bg-secondary bg-opacity-75 text-white ">
    <div class="row">
        <div class="col">
            ${name}
        </div>
        <div class="col">
            <div class="text-end">
                ${phone}
            </div>
        </div>
        <div class="col-auto">
            <div class="btn-group">
                <button class="btn btn-secondary btn-sm dropdown-toggle" type="button" data-bs-toggle="dropdown"
                        aria-expanded="false">
                    ${sessionScope.locale}
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item" href="${path}/controller?command=change_locale&language=EN">${en}</a>
                    </li>
                    <li><a class="dropdown-item" href="${path}/controller?command=change_locale&language=RU">${ru}</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <c:if test="${not empty sessionScope.current_role}">
                <jsp:include page="header_navigation.jsp"/>
            </c:if>
        </div>
    </div>
</div>
<script src="${path}/bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
