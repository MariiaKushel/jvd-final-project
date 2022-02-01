<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="language.en" var="en"/>
<fmt:message key="language.ru" var="ru"/>
<fmt:message key="message.name" var="name"/>
<fmt:message key="message.need_account_to_book1" var="info_part1"/>
<fmt:message key="message.need_account_to_book2" var="info_part2"/>
<fmt:message key="message.need_account_to_book3" var="info_part3"/>
<fmt:message key="message.need_account_to_book4" var="info_part4"/>
<fmt:message key="message.need_account_to_book5" var="info_part5"/>
<fmt:message key="message.phone" var="phone"/>
<fmt:message key="title.header" var="title"/>

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
            <a class="link-light text-decoration-none" href="${path}/controller?command=go_to_main_page">
                ${name}
            </a>
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
                    ${locale=='ru_RU'?ru:en}
                </button>
                <ul class="dropdown-menu">
                    <li><a class="dropdown-item text-secondary"
                           href="${path}/controller?command=change_locale&language=EN">${en}</a>
                    </li>
                    <li><a class="dropdown-item text-secondary"
                           href="${path}/controller?command=change_locale&language=RU">${ru}</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col text-center">
            <c:if test="${not empty current_role}">
                <jsp:include page="header_navigation.jsp"/>
            </c:if>
            <c:if test="${empty current_role}">
                ${info_part1}
                <a class="link-light" href="${path}/controller?command=go_to_sing_in_page">
                        ${info_part2}
                </a>
                ${info_part3}
                <a class="link-light"
                   href="${path}/controller?command=go_to_create_new_account_page">
                        ${info_part4}
                </a>
                ${info_part5}
            </c:if>
        </div>
    </div>
</div>
<script src="${path}/bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
