<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.sing_in" var="title"/>
<fmt:message key="message.incorrect_login_or_password" var="incorrect_login_or_password"/>
<fmt:message key="field.email" var="email"/>
<fmt:message key="field.password" var="password"/>
<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="reference.back_to_main" var="back_to_main"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="message.email_rules" var="email_rules"/>
<fmt:message key="reference.show_password" var="show_password"/>
<fmt:message key="reference.hide_password" var="hide_password"/>

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
        <div class="col text-danger">
            <c:if test="${wrong_login_or_password eq true}">
                ${incorrect_login_or_password}
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
                <input type="hidden" name="command" value="sing_in"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${email}
                    </label>
                    <input type="text" maxlength="50" name="email"
                           pattern="[\da-z]([\da-z_\-\.]*)[\da-z_\-]@[\da-z_\-]{2,}\.[a-z]{2,6}"
                           required oninvalid="this.setCustomValidity('${email_rules}')"
                           class="form-control">
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${password}
                    </label>
                    <input type="password" minlength="4" maxlength="12" name="password" id="pass"
                           pattern="[\da-zA-Z\-!«»#\$%&'\(\)\*\+,\./:;<=>\?@_`\{\|\}~]+"
                           required oninvalid="this.setCustomValidity('${password_rules} \'')"
                           class="form-control">
                    <label class="form-label">
                        <a class="link-secondary text-decoration-none" id="show">
                            ${show_password}
                        </a>
                    </label>
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
        <div class="col mb-3">
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
