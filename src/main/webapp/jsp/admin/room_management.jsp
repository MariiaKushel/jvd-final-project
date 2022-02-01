<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.find" var="find"/>
<fmt:message key="field.num_sleeping_place" var="num_sleeping_place"/>
<fmt:message key="field.price_from" var="pricefrom"/>
<fmt:message key="field.price_to" var="priceto"/>
<fmt:message key="field.room_number" var="number"/>
<fmt:message key="field.visible" var="visible_flag"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.new_search" var="new_search"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.number_rules" var="number_rules"/>
<fmt:message key="message.price_rules" var="price_rules"/>
<fmt:message key="reference.create_new" var="create_new"/>
<fmt:message key="reference.find_all" var="find_all"/>
<fmt:message key="reference.update" var="update_table"/>
<fmt:message key="reference.update" var="update_table"/>
<fmt:message key="room.id" var="id_table"/>
<fmt:message key="room.number" var="number_table"/>
<fmt:message key="room.price" var="price_table"/>
<fmt:message key="room.rating" var="rating_table"/>
<fmt:message key="room.sleeping_place" var="sleeping_place_table"/>
<fmt:message key="room.visible" var="visible_table"/>
<fmt:message key="title.room_management" var="title"/>

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
    <div class="fw-bold mb-3">
        ${title}
    </div>
    <div class="row">
        <div class="col-sm-3 mb-3 bg-secondary opacity-75 text-white">
            <div class="row">
                <div class="col mb-3">
                    <form method="get" action="${path}/controller">
                        <input type="hidden" name="command" value="find_all_rooms"/></br>
                        <button type="submit" class="btn btn-light">
                            ${find_all}
                        </button>
                    </form>
                </div>
                <div class="col-auto mb-3">
                    <form method="get" action="${path}/controller">
                        <input type="hidden" name="command" value="go_to_create_room_page"/></br>
                        <button type="submit" class="btn btn-light">
                            ${create_new}
                        </button>
                    </form>
                </div>
            </div>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_room_by_number"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${number}
                    </label>
                    <div class="row">
                        <div class="col">
                            <input type="number" min="1" max="99999" name="room_number" pattern="//d{1,5}"
                                   value="${search_parameter_atr['room_number_atr']}"
                                   oninvalid="this.setCustomValidity('${number_rules}')"
                                   class="form-control"/>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                    <label class="form-label text-dark">
                        <c:if test="${not empty search_parameter_atr['wrong_number_atr']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>
            </form>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_room_by_sleeping_place"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${num_sleeping_place}
                    </label>
                    <div class="row">
                        <div class="col">
                            <select class="form-select" name="sleeping_places">
                                <option value="" label="" selected></option>
                                <c:forEach var="place" items="${all_places_ses}">
                                    <option value="${place}"
                                        ${search_parameter_atr['sleeping_places_atr'] eq place? 'selected': ' '}>
                                            ${place}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
            </form>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_room_by_visible"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${visible_flag}
                    </label>
                    <div class="row">
                        <div class="col">
                            <select class="form-select" name="visible">
                                <option value="" label="" selected></option>
                                <c:forEach var="cur_prepayment" items="${visibility_ses}">
                                    <option value="${cur_prepayment}"
                                        ${not empty search_parameter_atr['visible_atr'] and search_parameter_atr['visible_atr'] eq cur_prepayment? 'selected': ' '}>
                                            ${cur_prepayment}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
            </form>

            <form method="get" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="find_room_by_price_range"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${pricefrom}
                    </label>
                    <input type="number" step=0.01 min="0" max="9999999.99" name="price_from"
                           value="${search_parameter_atr['price_from_atr']}"
                           oninvalid="this.setCustomValidity('${price_rules} 0 - 9999999.99')"
                           class="form-control"/>
                    <label class="form-label">
                        ${priceto}
                    </label>
                    <div class="row">
                        <div class="col">
                            <input type="number" step=0.01 min="0" max="9999999.99" name="price_to"
                                   value="${search_parameter_atr['price_to_atr']}"
                                   oninvalid="this.setCustomValidity('${price_rules} 0 - 9999999.99')"
                                   class="form-control"/>
                            <label class="form-label text-dark">
                                <c:if test="${not empty search_parameter_atr['wrong_price_range_atr']}">
                                    ${incorrect_data}
                                </c:if>
                            </label>
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-light">
                                ${find}
                            </button>
                        </div>
                    </div>
                </div>
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
                    <table class="table text-secondary border-secondary">
                        <thead>
                        <tr>
                            <th scope="col">${id_table}</th>
                            <th scope="col">${number_table}</th>
                            <th scope="col">${sleeping_place_table}</th>
                            <th scope="col">${price_table}</th>
                            <th scope="col">${rating_table}</th>
                            <th scope="col">${visible_table}</th>
                            <th scope="col">${update_table}</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="element" items="${room_list_atr}">
                            <tr>
                                <td>${element.entityId}</td>
                                <td>${element.number}</td>
                                <td>${element.sleepingPlace}</td>
                                <td>${element.pricePerDay}</td>
                                <td>${element.rating}</td>
                                <td>${element.visible}</td>
                                <td><a class="text-secondary text-decoration-none"
                                       href="${path}/controller?command=go_to_update_room_page&room_id=${element.entityId}">
                                        ${update_table}</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
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
