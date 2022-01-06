<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.create_new_account" var="title"/>
<fmt:message key="message.wrong_data" var="wrong_data"/>
<fmt:message key="field.email" var="email"/>
<fmt:message key="field.name" var="name"/>
<fmt:message key="field.phone_number" var="phone_number"/>
<fmt:message key="field.password" var="password"/>
<fmt:message key="field.repeat_password" var="repeat_password"/>
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
                ${wrong_data}
            </c:if>
        </div>
        <div class="col">
        </div>
    </div>
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
            <form method="post" action="${path}/controller">
                <input type="hidden" name="command" value="create_new_account"/>
                <div class="mb-2">
                    <label class="form-label">
                        ${email}
                    </label>
                    <input type="text" name="email" class="form-control text-lowercase">
                </div>
                <div class="mb-2">
                    <label class="form-label">
                        ${name}
                    </label>
                    <input type="text" name="name" class="form-control">
                </div>
                <div class="mb-2">
                    <label class="form-label">
                        ${phone_number}
                    </label>
                    <input type="text" name="phone_number" class="form-control">
                </div>
                <div class="mb-2">
                    <label class="form-label">
                        ${password}
                    </label>
                    <input type="text" name="password" class="form-control">
                </div>
                <div class="mb-2">
                    <label class="form-label">
                        ${repeat_password}
                    </label>
                    <input type="text" name="repeat_password" class="form-control">
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
