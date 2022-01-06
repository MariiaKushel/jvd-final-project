<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.sing_in" var="title"/>
<fmt:message key="message.wrong_login_or_password" var="wrong_login_or_password"/>
<fmt:message key="field.email" var="email"/>
<fmt:message key="field.password" var="password"/>
<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="reference.back_to_main" var="back_to_main"/>

<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <title>
        ${title}
    </title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>

<div class="container text-secondary text-center">
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
            <c:if test="${sessionScope.wrong_message eq true}">
                ${wrong_login_or_password}
            </c:if>
        </div>
        <div class="col">
        </div>
    </div>
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
            <form name="SingInForm" method="post" action="${path}/controller">
                <input type="hidden" name="command" value="sing_in"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${email}
                    </label>
                    <input type="text" name="email" class="form-control text-lowercase">
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${password}
                    </label>
                    <input type="text" name="password" class="form-control">
                </div>
                <button type="submit" class="btn btn-secondary">
                    ${confirm}
                </button>
            </form>
        </div>
        <div class="col">
        </div>
    </div>
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
            <a class="link-secondary text-decoration-none" href="${path}/controller?command=go_to_main_page">
                ${back_to_main}
            </a>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
