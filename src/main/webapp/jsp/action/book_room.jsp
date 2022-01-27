<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.book_room" var="title"/>
<fmt:message key="field.date_from" var="date_from"/>
<fmt:message key="field.date_to" var="date_to"/>
<fmt:message key="field.num_sleeping_place" var="num_sleeping_place"/>
<fmt:message key="field.price_from" var="price_from"/>
<fmt:message key="field.price_to" var="price_to"/>
<fmt:message key="button.find" var="find"/>
<fmt:message key="message.date_rules" var="date_rules"/>
<fmt:message key="message.price_rules" var="price_rules"/>
<fmt:message key="message.incorrect_date_or_price_range" var="incorrect_date_or_price_range"/>
<fmt:message key="message.room_number" var="number"/>
<fmt:message key="message.room_sleeping_place" var="room_sleeping_place"/>
<fmt:message key="message.room_description" var="room_description"/>
<fmt:message key="message.room_price" var="price"/>
<fmt:message key="message.room_rating" var="rating"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="message.not_found" var="not_found"/>

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
    <div class="row">
        <div class="col-sm-3 mb-3 bg-secondary opacity-75 text-white">
            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_room_by_parameter"/>
                <div class="text-dark">
                    <c:if test="${not empty search_parameter_atr['wrong_date_or_price_range_atr']}">
                        ${incorrect_date_or_price_range}
                    </c:if>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${date_from}
                    </label>
                    <input type="date" min="${today}" name="date_from" value="${search_parameter_atr['date_from_atr']}"
                           required oninvalid="this.setCustomValidity('${date_rules}')"
                           class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${date_to}
                    </label>
                    <input type="date" min="${tomorrow}" name="date_to" value="${search_parameter_atr['date_to_atr']}"
                           required oninvalid="this.setCustomValidity('${date_rules}')"
                           class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${num_sleeping_place}
                    </label></br>
                    <c:forEach var="place" items="${all_places_ses}">
                        <div class="form-check form-check-inline">
                            <input type="checkbox" class="form-check-input" name="sleeping_places"
                                   value="${place}">
                            <label class="form-check-label">${place}</label>
                        </div>
                        </br>
                    </c:forEach>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${price_from}
                    </label>
                    <input type="number" step="0.01" min="${min_price_ses}" max="${max_price_ses}" name="price_from"
                           value="${search_parameter_atr['price_from_atr']}"
                           oninvalid="this.setCustomValidity('${price_rules} ${min_price_ses}-${max_price_ses}')"
                           class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${price_to}
                    </label>
                    <input type="number" step="0.01" min="${min_price_ses}" max="${max_price_ses}" name="price_to"
                           value="${search_parameter_atr['price_to_atr']}"
                           oninvalid="this.setCustomValidity('${price_rules} ${min_price_ses}-${max_price_ses}')"
                           class="form-control">
                </div>
                <button type="submit" class="btn btn-light">
                    ${find}
                </button>
            </form>
        </div>
        <div class="col-sm-9 mb-3">
            <c:choose>
                <c:when test="${not empty new_search_atr and empty room_list_atr}">
                    ${new_search}
                </c:when>
                <c:when test="${empty new_search_atr and empty room_list_atr}">
                    ${not_found}
                </c:when>
                <c:otherwise>

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
                                               href="${path}/controller?command=find_room_by_id&room_id=${room.entityId}&date_from=${search_parameter_atr['date_from_atr']}&date_to=${search_parameter_atr['date_to_atr']}">
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
                    <div class="text-danger">
                        <c:if test="${wrong_date_or_price_range_atr eq true}">
                            ${incorrect_date_or_price_range}
                        </c:if>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="../footer/footer.jsp"/>
</footer>
</body>
</html>
