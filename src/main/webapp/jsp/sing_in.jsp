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
<header>
    <jsp:include page="header.jsp"/>
</header>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>${title}</title>
</head>
<body>
<div class="container text-secondary text-center">
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
           <c:if test="${sessionScope.wrong_message eq true}">${wrong_login_or_password}</c:if>
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
                    <label for="input_email" class="form-label">${email}</label>
                    <input type="text" name="email" class="form-control " id="input_email">
                </div>
                <div class="mb-3">
                    <label for="input_password" class="form-label">${password}</label>
                    <input type="text" name="password" class="form-control" id="input_password">
                </div>
                <button type="submit" class="btn btn-secondary">${confirm}</button>
            </form>
        </div>
        <div class="col">
        </div>
    </div>
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
            <a href="${path}/controller?command=go_to_main_page"
               class="link-secondary text-decoration-none">${back_to_main}</a>
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
