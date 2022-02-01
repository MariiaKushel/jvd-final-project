<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="field.email" var="email"/>
<fmt:message key="field.password" var="password"/>
<fmt:message key="message.email_rules" var="email_rules"/>
<fmt:message key="message.incorrect_login_or_password" var="incorrect_login_or_password"/>
<fmt:message key="message.not_found" var="not_found"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="reference.back_to_main" var="back_to_main"/>
<fmt:message key="reference.hide_password" var="hide_password"/>
<fmt:message key="reference.show_password" var="show_password"/>
<fmt:message key="title.sing_in" var="title"/>


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
    <jsp:include page="header/header.jsp"/>
</header>

<div class="container text-secondary text-center">
    <div class="mb-3 fw-bold">
        ${title}
    </div>
    <div class="mb-3 text-danger">
        <c:if test="${not empty user_data_ses['wrong_email_or_password_ses']}">
            ${incorrect_login_or_password}
        </c:if>
        <c:if test="${not empty user_data_ses['not_found_ses']}">
            ${not_found}
        </c:if>
    </div>
    <div class="row">
        <div class="col mb-3">
        </div>
        <div class="col mb-3">
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="sing_in"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${email}
                    </label>
                    <input type="text" maxlength="50" name="email" value="${user_data_ses['email_ses']}"
                           pattern="[\da-z]([\da-z_\-\.]*)[\da-z_\-]@[\da-z_\-]{2,}\.[a-z]{2,6}"
                           required oninvalid="this.setCustomValidity('${email_rules}')"
                           class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${password}
                    </label>
                    <input type="password" minlength="4" maxlength="12" name="password" id="pass"
                           value="${user_data_ses['password_ses']}"
                           pattern="[\da-zA-Z\-!«»#\$%&'\(\)\*\+,\./:;=\?@_`\{\|\}~]+"
                           required oninvalid="this.setCustomValidity('${password_rules} \'')"
                           class="form-control">
                    <label class="form-label">
                        <a class="link-secondary text-decoration-none" id="show">
                            ${show_password}
                        </a>
                    </label>
                </div>
                <div class="mb-3">
                    <button type="submit" class="btn btn-secondary">
                        ${confirm}
                    </button>
                </div>
            </form>
        </div>
        <div class="col mb-3">
        </div>
    </div>
    <div class="mb-3">
        <a class="link-secondary text-decoration-none" href="${path}/controller?command=go_to_main_page">
            ${back_to_main}</a>
    </div>
</div>

<footer>
    <jsp:include page="footer/footer.jsp"/>
</footer>

<script type="text/javascript">
    var input = document.getElementById("pass");
    var ref = document.getElementById("show");
    ref.onclick = show;

    function show() {
        if (input.getAttribute('type') == 'password') {
            input.removeAttribute('type');
            input.setAttribute('type', 'text');
            ref.innerHTML = '${hide_password}';
        } else {
            input.removeAttribute('type');
            input.setAttribute('type', 'password');
            ref.innerHTML = '${show_password}';
        }
    }
</script>
</body>
</html>
