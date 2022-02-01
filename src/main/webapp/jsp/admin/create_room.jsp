<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.new_price_rules" var="new_price_rules"/>
<fmt:message key="message.not_data" var="not_data"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.number_rules" var="number_rules"/>
<fmt:message key="room.number" var="number_table"/>
<fmt:message key="room.price" var="price_table"/>
<fmt:message key="room.sleeping_place" var="sleeping_place_table"/>
<fmt:message key="room.visible" var="visible_table"/>
<fmt:message key="title.create_room" var="title"/>

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
        <c:when test="${not empty create_room_result}">
            ${create_room_result eq true? complete: failed}
        </c:when>
        <c:otherwise>

            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="create_room"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${number_table}
                    </label>
                    <div class="col-md-10">
                        <input type="number" min="1" max="99999" name="room_number" pattern="//d{1,5}"
                               value="${room_data_ses['room_number_ses']}"
                               required oninvalid="this.setCustomValidity('${number_rules}')"
                               class="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty room_data_ses['wrong_number_ses']}">
                            ${incorrect_data}
                        </c:if>
                    </div>
                </div>

                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${sleeping_place_table}
                    </label>
                    <div class="col-md-10">
                        <input type="number" min="1" max="99999" name="sleeping_place" pattern="//d{1,5}"
                               value="${room_data_ses['sleeping_place_ses']}"
                               required oninvalid="this.setCustomValidity('${number_rules}')"
                               class="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty room_data_ses['wrong_sleeping_place_ses']}">
                            ${incorrect_data}
                        </c:if>
                    </div>
                </div>

                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${price_table}
                    </label>
                    <div class="col-md-10">
                        <input type="number" step="0.01" min="0" max="9999999.99" name="price"
                               value="${room_data_ses['price_ses']}"
                               required oninvalid="this.setCustomValidity('${new_price_rules}')"
                               class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty room_data_ses['wrong_price_ses']}">
                            ${incorrect_data}
                        </c:if>
                    </div>
                </div>

                <div class="mb-3">
                    <input type="checkbox" class="form-check-input " name="visible" value="true"/>
                    <label class="form-label mb-3">${visible_table}</label>
                </div>

                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary">
                            ${confirm}
                    </button>
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
