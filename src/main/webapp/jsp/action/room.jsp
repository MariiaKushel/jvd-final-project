<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<c:set var="language" value="${locale}"/>

<fmt:message key="reference.back_to_show_room" var="back_to_show_room"/>
<fmt:message key="reference.back_to_book_room" var="back_to_book_room"/>
<fmt:message key="message.room_number" var="number"/>
<fmt:message key="message.room_sleeping_place" var="room_sleeping_place"/>
<fmt:message key="message.room_description" var="room_description"/>
<fmt:message key="message.room_price" var="price"/>
<fmt:message key="message.room_rating" var="rating"/>
<fmt:message key="message.room_reviews" var="reviews"/>
<fmt:message key="message.incorrect_room" var="incorrect_room"/>
<fmt:message key="message.mark" var="mark"/>
<fmt:message key="message.no_comments" var="no_comments"/>
<fmt:message key="button.make_order" var="make_order"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>

<div class="container text-secondary ">
    <div class="row">
        <c:if test="${empty current_role}">
            <div class="col mb-3 text-end">
                <a class="link-secondary text-decoration-none"
                   href="${path}/controller?command=find_all_visible_rooms">
                        ${back_to_show_room}
                </a>
            </div>
        </c:if>
        <c:if test="${not empty date_from_atr}">
            <div class="col mb-3 text-center">
                <form method="get" action="${path}/controller">
                    <input type="hidden" name="command" value="go_to_order_page"/>
                    <input type="hidden" name="date_from" value="${date_from_atr}"/>
                    <input type="hidden" name="date_to" value="${date_to_atr}"/>
                    <input type="hidden" name="room_id" value="${room_atr.entityId}"/>
                    <input type="hidden" name="room_number" value="${room_atr.number}"/>
                    <input type="hidden" name="room_price" value="${room_atr.pricePerDay}"/>
                    <button type="submit" class="btn btn-secondary">
                       ${make_order}
                    </button>
                </form>
            </div>
        </c:if>
    </div>
    <c:choose>
        <c:when test="${empty room_not_found_atr}">
            <div class="row">
                <div class="col mb-3">
                        ${number} ${room_atr.number}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${rating}: ${room_atr.rating}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${room_sleeping_place}: ${room_atr.sleepingPlace}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${price}: ${room_atr.pricePerDay}
                </div>
            </div>
            <div class="row">
                <div class="col mb-3">
                        ${room_description}: ${locale=='ru_RU'?description_atr.descriptionRu:description_atr.descriptionEn}
                </div>
            </div>
            <c:forEach var="current_image" items="${image_list_atr}">
                <div class="row">
                    <div class="col mb-3">
                        <img src="${current_image}" class="figure-img mx-auto d-block" style="width: 600px">
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty image_list_atr}">
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
            <c:forEach var="current_review" items="${review_list_atr}">
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
            <c:if test="${empty review_list_atr}">
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
</div>

<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
