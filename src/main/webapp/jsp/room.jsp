<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

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

<div class="container text-secondary">
    <c:if test="${sessionScope.wrong_message eq false}">
        <div class="row">
            <div class="col">
                <img src="${path}/images/nophoto_medium.png" class="rounded">
            </div>
        </div>
        <div class="row">
            <div class="col">
                Room number №${room.number}
            </div>
        </div>
        <div class="row">
            <div class="col">
                Sleeping place: ${room.sleepingPlace}
            </div>
        </div>
        <div class="row">
            <div class="col">
                Price: ${room.pricePerDay}
            </div>
        </div>
        <div class="row">
            <div class="col">
                Description: ${room.description}
            </div>
        </div>

        <div class="row">
            <div class="col">
                <a class="link-dark text-decoration-none" href="${path}/controller?command=find_all_visible_rooms">
                    Back to room list
                </a>
            </div>
        </div>
    </c:if>
    <c:if test="${sessionScope.wrong_message eq true}">
        Sorry sach room not found
    </c:if>
</div>


<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
