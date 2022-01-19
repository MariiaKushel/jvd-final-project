<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.header" var="title"/>
<fmt:message key="reference.home" var="home"/>
<fmt:message key="reference.book_room" var="book_room"/>
<fmt:message key="reference.our_rooms" var="our_room"/>
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
            <a class="nav-link text-secondary" href="${path}/controller?command=find_all_visible_rooms">
                ${our_room}
            </a>
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_book_room_page">
                ${book_room}
            </a>
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_contact_page">
                ${contact}
            </a>
        </ul>
    </div>
    <div class="col-auto">
        <ul class="nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle text-white" data-bs-toggle="dropdown"
                   href="${path}/controller?command=go_to_account_page" role="button" aria-expanded="false">
                    User: ${current_user}
                </a>
                <c:if test="${current_role eq 'CLIENT'}">
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_account_page">Account</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Change
                            password</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Replenish
                            balance</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=sing_out">Orders</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Leave
                            review</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Sing
                            out</a></li>
                    </ul>
                </c:if>
                <c:if test="${current_role eq 'ADMIN'}">
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Change
                            password</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Users management</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=sing_out">Orders management</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Room management</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Review management</a></li>
                        <li><a class="dropdown-item text-secondary" href="${path}/controller?command=sing_out">Sing
                            out</a></li>
                    </ul>
                </c:if>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
