<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="reference.back_to_main" var="back_to_main"/>
<fmt:message key="reference.previous" var="previous"/>
<fmt:message key="reference.next" var="next"/>
<fmt:message key="room.number" var="number"/>
<fmt:message key="room.price" var="price"/>
<fmt:message key="room.rating" var="rating"/>
<fmt:message key="room.sleeping_place" var="room_sleeping_place"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="title.our_room" var="title"/>


<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<div class="container text-secondary">
    <div class="mb-3 fw-bold">
        ${title}
    </div>
    <c:choose>
        <c:when test="${empty room_list_atr}">
            ${not_found}
        </c:when>
        <c:otherwise>

            <div class="row">
                <div class="col mb-3">
                    <c:if test="${pagination_ses['current_sheet_ses']>1}">
                        <a class="link-secondary text-decoration-none"
                           href="${path}/controller?command=find_all_visible_rooms&direction=-1">
                            ${previous}</a>
                    </c:if>
                </div>
                <div class="col mb-3">
                    ${pagination_ses['current_sheet_ses']}
                </div>
                <div class="col-auto mb-3">
                    <c:if test="${pagination_ses['current_sheet_ses']<pagination_ses['all_sheet_ses']}">
                        <a class="link-secondary text-decoration-none"
                           href="${path}/controller?command=find_all_visible_rooms&direction=1">
                            ${next}</a>
                    </c:if>
                </div>
            </div>

            <c:forEach var="room" items="${room_list_atr}">
                <div class="card mb-3">
                    <div class="row g-0">
                        <div class="col-auto" style="width: 150px;">
                            <c:choose>
                                <c:when test="${not empty room.preview}">
                                    <img src="${room.preview}" class="img-thumbnail">
                                </c:when>
                                <c:otherwise>
                                    <img src="${path}/images/nophoto.jpg" class="img-thumbnail">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col">
                            <div class="card-body">
                                <h5 class="card-title card-text">
                                    <a class="link-secondary text-decoration-none"
                                       href="${path}/controller?command=find_room_by_id&room_id=${room.entityId}">
                                            ${number} ${room.number}
                                    </a>
                                </h5>
                                <p class="card-text">
                                        ${room_sleeping_place}: ${room.sleepingPlace}</br>
                                        ${price}: ${room.pricePerDay}
                                </p>
                            </div>
                        </div>
                        <div class="col-auto">
                            <div class="card-body">
                                <h5 class="card-title text-end">
                                        ${rating}: ${room.rating}
                                </h5>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
