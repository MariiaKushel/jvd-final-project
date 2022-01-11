<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.find_room" var="title"/>

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
    <jsp:include page="header.jsp"/>
</header>
<body>
<div class="container text-secondary" <%--style="overflow-y: scroll;--%>>
    <div class="fw-bold">
        find room
    </div>

    <div class="card mb-3" >
        <div class="row g-0">
            <div class="col-md-3">
                <img src="${path}/images/nophoto.png" >
            </div>
            <div class="col-md-9">
                <div class="card-body">
                    <h5 class="card-title">Number name</h5>
                    <p class="card-text">Sleeping place: ...</br>
                        Raiting: ...</br>
                        Price: ...</p>
                </div>
            </div>
        </div>
    </div>
    <div class="card mb-3" >
        <div class="row g-0">
            <div class="col-md-3">
                <img src="${path}/images/nophoto.png" >
            </div>
            <div class="col-md-9">
                <div class="card-body">
                    <h5 class="card-title">Number name</h5>
                    <p class="card-text">Sleeping place: ...</br>
                        Raiting: ...</br>
                        Price: ...</p>
                </div>
            </div>
        </div>
    </div>
    <div class="card mb-3" >
        <div class="row g-0">
            <div class="col-md-3">
                <img src="${path}/images/nophoto.png" >
            </div>
            <div class="col-md-9">
                <div class="card-body">
                    <h5 class="card-title">Number name</h5>
                    <p class="card-text">Sleeping place: ...</br>
                        Raiting: ...</br>
                        Price: ...</p>
                </div>
            </div>
        </div>
    </div>



</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
