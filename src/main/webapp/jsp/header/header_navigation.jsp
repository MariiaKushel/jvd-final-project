<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="message.user" var="user"/>
<fmt:message key="reference.book_room" var="book_room"/>
<fmt:message key="reference.contact" var="contact"/>
<fmt:message key="reference.home" var="home"/>
<fmt:message key="reference.our_rooms" var="our_room"/>
<fmt:message key="reference.sing_out" var="sing_out"/>
<fmt:message key="title.account" var="account"/>
<fmt:message key="title.change_password" var="change_password"/>
<fmt:message key="title.discount_management" var="discount_management"/>
<fmt:message key="title.header" var="title"/>
<fmt:message key="title.order_management" var="order_management"/>
<fmt:message key="title.order_management" var="order_management"/>
<fmt:message key="title.orders" var="orders"/>
<fmt:message key="title.replenish_balance" var="replenish_balance"/>
<fmt:message key="title.review_management" var="review_management"/>
<fmt:message key="title.room_management" var="room_management"/>
<fmt:message key="title.user_management" var="user_management"/>

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
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_home_page">${home}</a>
            <a class="nav-link text-secondary" href="${path}/controller?command=find_all_visible_rooms">${our_room}</a>
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_book_room_page">${book_room}</a>
            <a class="nav-link text-secondary" href="${path}/controller?command=go_to_contact_page">${contact}</a>
        </ul>
    </div>
    <div class="col-auto">
        <ul class="nav">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle text-white" data-bs-toggle="dropdown"
                   href="${path}/controller?command=go_to_account_page" role="button" aria-expanded="false">
                    ${user}: ${current_email}
                </a>
                <c:if test="${current_role eq 'CLIENT'}">
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_account_page">
                                ${account}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_change_password_page">
                                ${change_password}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_replenish_balance_page">
                                ${replenish_balance}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_client_orders_page">
                                ${orders}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=sing_out">
                                ${sing_out}</a></li>
                    </ul>
                </c:if>
                <c:if test="${current_role eq 'ADMIN'}">
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_change_password_page">
                                ${change_password}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_user_management_page">
                                ${user_management}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_order_management_page">
                                ${order_management}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_room_management_page">
                                ${room_management}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_review_management_page">
                                ${review_management}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=go_to_discount_management_page">
                                ${discount_management}</a></li>
                        <li><a class="dropdown-item text-secondary"
                               href="${path}/controller?command=sing_out">
                                ${sing_out}</a></li>
                    </ul>
                </c:if>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
