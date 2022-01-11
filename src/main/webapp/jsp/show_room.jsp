<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="reference.back_to_main" var="back_to_main"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Title</title> <%--FIXME add text to property--%>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
<div class="container text-secondary">
    <div class="row">
        <div class="col md-3">
            <h4 class="fw-bold">Our rooms</h4> <%--FIXME add text to property--%>
        </div>
        <div class="col md-3 text-end">
            <a class="link-secondary text-decoration-none" href="${path}/controller?command=go_to_main_page">
                ${back_to_main}
            </a>
        </div>
    </div>
    <c:forEach var="element" items="${room_list}">
        <div class="card mb-3">
            <div class="row g-0">
                <div class="col-auto" style="width: 150px;">
                    <img src="${preview_map[element.entityId]}" class="img-thumbnail"> <%--FIXME--%>
                </div>
                <div class="col">
                    <div class="card-body">
                        <h5 class="card-title card-text">
                            <a class="link-secondary text-decoration-none"
                               href="${path}/controller?command=find_room_by_id&room_id=${element.entityId}">
                                Number №${element.number}
                            </a>
                        </h5>
                        <p class="card-text">
                            Sleeping place: ${element.sleepingPlace}</br>
                            Price: ${element.pricePerDay}
                        </p>
                    </div>
                </div>
                <div class="col-auto">
                    <div class="card-body">
                        <h5 class="card-title text-end">
                            Rating: ${element.rating}
                        </h5>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    <div class="row">
        <div class="col mb-3 text-end">
            <a class="link-secondary text-decoration-none" href="${path}/controller?command=go_to_main_page">
                ${back_to_main}
            </a>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
