<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="message.footer" var="footer"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container position-relative p-3 mb-2 bg-secondary bg-opacity-75 text-white text-end" id="foot_position">
    <div>${footer}</div>
</div>
<script type="text/javascript">
    if (document.body.scrollHeight > window.innerHeight) {
        document.getElementById("foot_position").className = "container position-relative p-3 mb-2 bg-secondary bg-opacity-75 text-white text-end";
    } else {
        document.getElementById("foot_position").className = "container position-absolute fixed-bottom p-3 mb-2 bg-secondary bg-opacity-75 text-white text-end";
    }
</script>
</body>
</html>
