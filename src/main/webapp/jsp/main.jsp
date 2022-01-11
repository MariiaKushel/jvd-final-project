<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.main" var="title"/>
<fmt:message key="message.welcom" var="message"/>
<fmt:message key="reference.show_rooms" var="show_rooms"/>
<fmt:message key="reference.sing_in" var="sing_in"/>
<fmt:message key="reference.create_new_account" var="create_new_account"/>
<fmt:message key="language.ru" var="ru"/>
<fmt:message key="language.en" var="en"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${path}/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${path}/css/main.css" rel="stylesheet">
    <title>${title}</title>
</head>
<body>
<c:if test="${not empty sessionScope.current_role}">
    <jsp:forward page="/controller?command=go_to_home_page"></jsp:forward>
</c:if>
<div class="container text-dark text-center fw-bold">
    <div class="row">
        <div class="col">
            <h1>
                ${message}
            </h1>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <h4>
                <a class="link-dark text-decoration-none" href="${path}/controller?command=find_all_visible_rooms">
                    ${show_rooms}
                </a>
            </h4>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <h4>
                <a class="link-dark text-decoration-none" href="${path}/controller?command=go_to_sing_in_page">
                    ${sing_in}
                </a>
            </h4>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h4>
                <a class="link-dark text-decoration-none"
                   href="${path}/controller?command=go_to_create_new_account_page">
                    ${create_new_account}
                </a>
            </h4>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h6>
                <a class="link-dark text-decoration-none"
                   href="${path}/controller?command=change_locale&language=EN">
                    ${en}
                </a>
                /
                <a class="link-dark text-decoration-none"
                   href="${path}/controller?command=change_locale&language=RU">
                    ${ru}
                </a>
            </h6>
        </div>
    </div>
</div>
</body>
</html>
