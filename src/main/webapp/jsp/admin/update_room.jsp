<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.make_preview" var="make_preview"/>
<fmt:message key="button.update" var="update"/>
<fmt:message key="button.upload" var="upload"/>
<fmt:message key="description.en" var="des_en_table"/>
<fmt:message key="description.id" var="des_id_table"/>
<fmt:message key="description.ru" var="des_ru_table"/>
<fmt:message key="message.complete" var="complete"/>
<fmt:message key="message.failed" var="failed"/>
<fmt:message key="message.incorrect_data" var="incorrect_data"/>
<fmt:message key="message.new_price_rules" var="new_price_rules"/>
<fmt:message key="message.not_data" var="not_data"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.number_rules" var="number_rules"/>
<fmt:message key="room.id" var="id_table"/>
<fmt:message key="room.number" var="number_table"/>
<fmt:message key="room.price" var="price_table"/>
<fmt:message key="room.rating" var="rating_table"/>
<fmt:message key="room.sleeping_place" var="sleeping_place_table"/>
<fmt:message key="room.visible" var="visible_table"/>
<fmt:message key="title.update_description" var="description_title"/>
<fmt:message key="title.update_image" var="image_title"/>
<fmt:message key="title.update_room" var="room_title"/>
<fmt:message key="title.upload_image" var="upload_title"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${room_title}
    </title>
</head>
<header>
    <jsp:include page="../header/header.jsp"/>
</header>
<body>
<div class="container text-secondary">

    <c:choose>
        <c:when test="${not empty not_fount_ses}">
            ${not_found}
        </c:when>
        <c:when test="${not empty update_room_result}">
            <div class="mb-3 fw-bold">${room_title}</div>
            ${update_room_result eq true? complete: failed}
        </c:when>
        <c:when test="${not empty update_description_result}">
            <div class="mb-3 fw-bold">${description_title}</div>
            ${update_description_result eq true? complete: failed}
        </c:when>
        <c:when test="${not empty update_image_result}">
            <div class="mb-3 fw-bold">${image_title}</div>
            ${update_image_result eq true? complete: failed}
        </c:when>
        <c:when test="${not empty upload_result}">
            <div class="mb-3 fw-bold">${upload_title}</div>
            ${upload_result eq true? complete: failed}
        </c:when>
        <c:otherwise>
            <div class="mb-3 fw-bold">${room_title}</div>

            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="update_room"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${id_table}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${room_data_ses['room_id_ses']}" class="form-control" disabled>
                    </div>
                </div>
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

                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${rating_table}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${room_data_ses['rating_ses']}" class="form-control" disabled>
                    </div>
                </div>

                <div class="mb-3">
                    <input type="checkbox" class="form-check-input " name="visible"
                           value="true" ${room_data_ses['visible_ses'] eq 'true'?'checked':''}/>
                    <label class="form-label mb-3">${visible_table}</label>
                </div>

                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary">
                            ${update}
                    </button>
                </div>
            </form>

            <div class="mb-3 fw-bold">${description_title}</div>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="update_description"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${des_id_table}
                    </label>
                    <div class="col-md-10">
                        <input type="text" value="${description_data_ses['description_id_ses']}" class="form-control"
                               disabled>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">
                            ${des_ru_table}
                    </label>
                    <textarea maxlength="500" name="description_ru" class="form-control" style="height: 100px">
                            ${description_data_ses['description_ru_ses']}
                    </textarea>
                    <label class="form-label text-danger">
                        <c:if test="${not empty description_data_ses['wrong_description_ru_ses']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>

                <div class="mb-3">
                    <label class="form-label">
                            ${des_en_table}
                    </label>
                    <textarea maxlength="500" name="description_en" class="form-control" style="height: 100px">
                            ${description_data_ses['description_en_ses']}
                    </textarea>
                    <label class="form-label text-danger">
                        <c:if test="${not empty description_data_ses['wrong_description_en_ses']}">
                            ${incorrect_data}
                        </c:if>
                    </label>
                </div>

                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary">
                            ${update}
                    </button>
                </div>
            </form>

            <div class="mb-3 fw-bold">${image_title}</div>
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="change_preview"/>
                <input type="hidden" name="room_id" value="${room_data_ses['room_id_ses']}"/>
                <div class="form-check">
                    <div class="row">
                        <c:forEach var="current_image" items="${image_data_ses}">
                            <div class="col-3 mb-3">
                                <div>
                                    <img src="${current_image.value}" class="img-thumbnail">
                                </div>
                                <div class="container">
                                    <label class="form-check-label">${make_preview}</label>
                                    <input class="form-check-input" type="radio" name="preview"
                                           value="${current_image.key}"
                                        ${preview_marker_ses eq current_image.key? 'checked':''}>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="container text-center">
                        <button type="submit" class="btn btn-secondary">
                                ${update}
                        </button>
                    </div>
                </div>
            </form>
            <c:if test="${empty image_data_ses}">
                <div class="row">
                    <div class="col mb-3">
                        //empty
                            <%-- <img src="${path}/images/nophoto.jpg" class="figure-img mx-auto d-block" style="width: 600px">--%>
                    </div>
                </div>
            </c:if>

            <form method="post" action="${path}/controller" enctype="multipart/form-data">
                <input type="hidden" name="command" value="upload_image"/>
                <input type="hidden" name="room_id" value="${room_data_ses['room_id_ses']}"/>
                <div class="input-group mb-3">
                    <input type="file" name="image" class="form-control">
                    <button type="submit" class="btn btn-outline-secondary">${upload}</button>
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
