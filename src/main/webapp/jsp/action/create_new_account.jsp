<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.create_new_account" var="title"/>
<fmt:message key="field.email" var="email"/>
<fmt:message key="field.name" var="name"/>
<fmt:message key="field.phone_number" var="phone_number"/>
<fmt:message key="field.password" var="password"/>
<fmt:message key="field.repeat_password" var="repeat_password"/>
<fmt:message key="button.confirm" var="confirm"/>
<fmt:message key="reference.back_to_main" var="back_to_main"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="message.email_rules" var="email_rules"/>
<fmt:message key="message.name_rules" var="name_rules"/>
<fmt:message key="message.phone_number_rules" var="phone_number_rules"/>
<fmt:message key="reference.show_password" var="show_password"/>
<fmt:message key="reference.hide_password" var="hide_password"/>
<fmt:message key="message.incorrect_email" var="incorrect_email"/>
<fmt:message key="message.incorrect_email_exist" var="incorrect_email_exist"/>
<fmt:message key="message.incorrect_name" var="incorrect_name"/>
<fmt:message key="message.incorrect_phone_number" var="incorrect_phone_number"/>
<fmt:message key="message.incorrect_password" var="incorrect_password"/>
<fmt:message key="message.incorrect_repeat_password" var="incorrect_repeat_password"/>
<fmt:message key="message.missmatch" var="missmatch"/>

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
    <jsp:include page="../header/header.jsp"/>
</header>

<div class="container text-secondary text-center">
    <div class="mb-3 fw-bold">
        ${title}
    </div>
    <div class="row">
        <div class="col">
        </div>
        <div class="col">
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="create_new_account"/>
                <div class="mb-3">
                    <label class="form-label">
                        ${email}
                    </label>
                    <input type="text" maxlength="50" name="email" value="${user_data_ses['email_ses']}"
                           pattern="[\da-z]([\da-z_\-\.]*)[\da-z_\-]@[\da-z_\-]{2,}\.[a-z]{2,6}"
                           required oninvalid="this.setCustomValidity('${email_rules}')"
                           class="form-control">
                    <label class="form-label text-danger">
                        <c:if test="${not empty user_data_ses['wrong_email_ses']}">
                            ${incorrect_email}
                        </c:if>
                        <c:if test="${not empty user_data_ses['wrong_email_exist_ses']}">
                            ${incorrect_email_exist}
                        </c:if>
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${name}
                    </label>
                    <input type="text" maxlength="50" name="name" value="${user_data_ses['name_ses']}"
                           pattern="[\wа-яА-яёЁ][\wа-яА-яёЁ\s]*"
                           required oninvalid="this.setCustomValidity('${name_rules}')"
                           class="form-control">
                    <label class="form-label text-danger">
                        <c:if test="${not empty user_data_ses['wrong_name_ses']}">
                            ${incorrect_name}
                        </c:if>
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${phone_number}
                    </label>
                    <input type="text" name="phone_number" value="${user_data_ses['phone_number_ses']}"
                           pattern="\+375(29|44|17|25|33)\d{7}"
                           required oninvalid="this.setCustomValidity('${phone_number_rules}')"
                           class="form-control">
                    <label class="form-label text-danger">
                        <c:if test="${not empty user_data_ses['wrong_phone_number_ses']}">
                            ${incorrect_phone_number}
                        </c:if>
                    </label>
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
                    <label class="form-label text-danger">
                        <c:if test="${not empty user_data_ses['wrong_password_ses']}">
                            ${incorrect_password}</br>
                        </c:if>
                    </label>
                    <label class="form-label">
                        <a class="link-secondary text-decoration-none" id="show">
                            ${show_password}
                        </a>
                    </label>
                </div>
                <div class="mb-3">
                    <label class="form-label">
                        ${repeat_password}
                    </label>
                    <input type="password" minlength="4" maxlength="12" name="repeat_password"
                           value="${user_data_ses['repeat_password_ses']}"
                           pattern="[\da-zA-Z\-!«»#\$%&'\(\)\*\+,\./:;=\?@_`\{\|\}~]+"
                           required oninvalid="this.setCustomValidity('${password_rules} \'')"
                           class="form-control">
                    <label class="form-label text-danger">
                        <c:if test="${not empty user_data_ses['wrong_missmatch_ses']}">
                            ${missmatch}
                        </c:if>
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
    <div class="mb-3">
        <a class="link-secondary text-decoration-none" href="${path}/controller?command=go_to_main_page">
            ${back_to_main}</a>
    </div>
</div>

<footer>
    <jsp:include page="../footer/footer.jsp"/>
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
