<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="reference.back_to_main" var="back_to_main"/>

<html>
<header>
    <jsp:include page="header.jsp"/>
</header>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>Error 500</title>
</head>
<body>
<div class="container">
    <table class="table text-secondary border-secondary">
        <thead>
        <tr>
            <th scope="col">Error 500</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Request from ${pageContext.errorData.requestURI} is failed</br>
                Servlet name: ${pageContext.errorData.servletName}</br>
                Status code: ${pageContext.errorData.statusCode}</br>
                Exception: ${pageContext.exception}</br>
                Message from exception: ${pageContext.exception.message}
            </td>
        </tr>
        </tbody>
    </table>
    <a class="link-secondary text-decoration-none"
       href="${path}/controller?command=go_to_main_page">${back_to_main}</a>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
