<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.header" var="title"/>
<fmt:message key="reference.home" var="home"/>
<fmt:message key="reference.find_room" var="find_room"/>
<fmt:message key="reference.contact" var="contact"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<div class="row justify-content-between">
    <div class="col-auto">
        <ul class="nav bg-white">
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_home_page">
                ${home}
            </a>
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_find_room_page">
                ${find_room}
            </a>
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_contact_page">
                ${contact}
            </a>
        </ul>
    </div>
    <div class="col-auto">
        <ul class="nav">
        <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle text-white" data-bs-toggle="dropdown" href="${path}/controller?command=go_to_account_page" role="button" aria-expanded="false">
            User: ${sessionScope.current_user}
        </a>
        <ul class="dropdown-menu">
            <li><a class="dropdown-item text-secondary" href="${path}/controller?command=go_to_account_page">Go to account</a></li>
            <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Sing out</a></li>
        </ul>
        </li>
        </ul>
    </div>
</div>
<%--<script src="${path}/bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"></script>--%>
</body>
</html>
