<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.account" var="title"/>

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
<div class="container text-secondary">
    <p class="fw-bold">account</p>
    <p>Hello ${sessionScope.current_user}</p>
    <p>ID ${sessionScope.current_user_id}</p>
    <p>Role ${sessionScope.current_role}</p>
    <p>Language ${sessionScope.locale}</p>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
