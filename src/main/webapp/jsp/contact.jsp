<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.contact" var="title"/>
<fmt:message key="message.hotel_official_name" var="official_name"/>
<fmt:message key="message.hotel_adress" var="adress"/>
<fmt:message key="message.hotel_phone" var="phone"/>
<fmt:message key="massage.hotel_bank_account" var="bank_account"/>

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
<div class="container text-secondary ">
   <div class ="row">
       <div class="col fw-bold">
           ${title}
       </div>
   </div>
    <div class ="row">
        <div class="col">
            ${official_name}
        </div>
    </div>
    <div class ="row">
        <div class="col">
            ${adress}
        </div>
    </div>
    <div class ="row">
        <div class="col">
            ${phone}
        </div>
    </div>
    <div class ="row">
        <div class="col">
            ${bank_account}
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
