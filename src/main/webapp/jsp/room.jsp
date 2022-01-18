<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<c:set var="language" value="${sessionScope.locale}"/>

<fmt:message key="reference.back_to_show_room" var="back_to_show_room"/>
<fmt:message key="reference.back_to_book_room" var="back_to_book_room"/>
<fmt:message key="message.room_number" var="number"/>
<fmt:message key="message.room_sleeping_place" var="sleeping_place"/>
<fmt:message key="message.room_description" var="room_description"/>
<fmt:message key="message.room_price" var="price"/>
<fmt:message key="message.room_rating" var="rating"/>
<fmt:message key="message.room_reviews" var="reviews"/>
<fmt:message key="message.incorrect_room" var="incorrect_room"/>
<fmt:message key="message.mark" var="mark"/>
<fmt:message key="message.no_comments" var="no_comments"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>

<div class="container text-secondary ">
    <div class="row">
        <c:if test="${empty sessionScope.current_role}">
            <div class="col mb-3 text-end">
                <a class="link-secondary text-decoration-none"
                   href="${path}/controller?command=find_all_visible_rooms">
                        ${back_to_show_room}
                </a>
            </div>
        </c:if>
        <c:if test="${not empty temp_date_from}">
            <div class="col mb-3 text-center">
                <form method="get" action="${path}/controller">
                    <input type="hidden" name="command" value="go_to_order_page"/>
                    <input type="hidden" name="date_from" value="${temp_date_from}"/>
                    <input type="hidden" name="date_to" value="${temp_date_to}"/>
                    <input type="hidden" name="room_id" value="${room.entityId}"/>
                    <input type="hidden" name="room_number" value="${room.number}"/>
                    <input type="hidden" name="room_price" value="${room.pricePerDay}"/>
                    <button type="submit" class="btn btn-secondary">
                        book it!
                    </button>
                </form>
            </div>
        </c:if>
    </div>
    <c:choose>
        <c:when test="${empty room_not_found}">
            <div class="row">
                <div class="col mb-3">
                        ${number} ${room.number}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${rating}: ${room.rating}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${sleeping_place}: ${room.sleepingPlace}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${price}: ${room.pricePerDay}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${room_description}: ${sessionScope.locale=='ru_RU'?description.descriptionRu:description.descriptionEn}
                </div>
            </div>
            <c:forEach var="current_image" items="${image_list}">
                <div class="row">
                    <div class="col mb-3">
                        <img src="${current_image}" class="figure-img mx-auto d-block" style="width: 600px">
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty image_list}">
                <div class="row">
                    <div class="col mb-3">
                        <img src="${path}/images/nophoto.jpg" class="figure-img mx-auto d-block" style="width: 600px">
                    </div>
                </div>
            </c:if>
            <div class="row">
                <div class="col mb-3 border-bottom border-3">
                        ${reviews}:
                </div>
            </div>
            <c:forEach var="current_review" items="${review_list}">
                <div class="row">
                    <div class="col mb-3 fw-bold">
                            ${current_review.author}
                    </div>
                    <div class="col mb-3 fw-bold">
                            ${mark}: ${current_review.roomMark}
                    </div>
                    <div class="col mb-3 text-end">
                            ${current_review.date}
                    </div>
                </div>
                <div class="row border-bottom border-3">
                    <div class="col mb-3">
                            ${current_review.reviewContent}
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty review_list}">
                <div class="row border-bottom border-3">
                    <div class="col mb-3">
                            ${no_comments}
                    </div>
                </div>
            </c:if>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="col mb-3">
                        ${incorrect_room}
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <div class="row">
        <div class="col mb-3 text-end">
            <a class="link-secondary text-decoration-none"
               href="${path}/controller?command=find_all_visible_rooms">
                ${back_to_book_room}
            </a>
        </div>
    </div>
</div>

<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
