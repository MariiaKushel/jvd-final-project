<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>

<fmt:message key="title.account" var="title"/>

<fmt:message key="field.email" var="user_email"/>
<fmt:message key="field.name" var="user_name"/>
<fmt:message key="field.phone_number" var="user_phone_number"/>
<fmt:message key="field.user_status" var="user_status"/>
<fmt:message key="field.role" var="user_role"/>
<fmt:message key="field.balance" var="user_balance"/>
<fmt:message key="field.discount" var="user_discount"/>
<fmt:message key="button.update" var="update"/>
<fmt:message key="message.name_rules" var="name_rules"/>
<fmt:message key="message.phone_number_rules" var="phone_number_rules"/>
<fmt:message key="message.incorrect_name" var="incorrect_name"/>
<fmt:message key="message.incorrect_phone_number" var="incorrect_phone_number"/>
<fmt:message key="message.password_rules" var="password_rules"/>
<fmt:message key="reference.show_password" var="show_password"/>
<fmt:message key="reference.hide_password" var="hide_password"/>
<fmt:message key="message.incorrect_name" var="incorrect_name"/>
<fmt:message key="message.incorrect_phone_number" var="incorrect_phone_number"/>
<fmt:message key="message.incorrect_password" var="incorrect_password"/>





<c:set var="email_value" value="${not empty user_atr?user_atr.email:email_ses}"/>
<c:set var="name_value" value="${not empty user_atr?user_atr.name:name_ses}"/>
<%--|${not empty user_atr?user_atr.phoneNumber:phone_number_ses}|</br>
|${not empty user_atr?user_atr.role:role_ses}|</br>
|${not empty user_atr?user_atr.status:user_status_ses}|</br>
|${not empty user_atr?user_atr.balance:balance_ses}|</br>
|${not empty user_atr?user_atr.discountId:discount_id_ses}|</br>
|${not empty rate_atr?rate_atr:rate_ses}|</br>--%>


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
    <jsp:include page="../../header/header.jsp"/>
</header>
<body>
<div class="container text-secondary">
<%--|${update_personal_data_result}|</br>
    |${not empty user_atr?user_atr.email:email_ses}|</br>
    |${not empty user_atr?user_atr.name:name_ses}|</br>
    |${not empty user_atr?user_atr.phoneNumber:phone_number_ses}|</br>
    |${not empty user_atr?user_atr.role:role_ses}|</br>
    |${not empty user_atr?user_atr.status:user_status_ses}|</br>
    |${not empty user_atr?user_atr.balance:balance_ses}|</br>
    |${not empty user_atr?user_atr.discountId:discount_id_ses}|</br>
    |${not empty rate_atr?rate_atr:rate_ses}|</br>--%>

    <c:choose>
        <c:when test="">${not empty update_personal_data_result}>
            Updated
        </c:when>
        <c:otherwise>
            <form method="post" action="${path}/controller" novalidate>
                <input type="hidden" name="command" value="update_personal_data"/>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_email}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="email" value="${email_value}"
                               class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_name}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="name" value="${not empty user_atr?user_atr.name:name_ses}"
                               pattern="[\wа-яА-яёЁ][\wа-яА-яёЁ\s]*"
                               required oninvalid="this.setCustomValidity('${name_rules}')"
                               class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty wrong_name_ses}">
                            ${incorrect_name}
                        </c:if>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_phone_number}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="phone_number"
                               value="${not empty user_atr?user_atr.phoneNumber:phone_number_ses}"
                               pattern="\+375(29|44|17|25|33)\d{7}"
                               required oninvalid="this.setCustomValidity('${phone_number_rules}')"
                               class="form-control">
                    </div>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty wrong_phone_number_ses}">
                            ${incorrect_phone_number}
                        </c:if>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_role}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="role" value="${not empty user_atr?user_atr.role:role_ses}"
                               class="form-control" disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_status}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="user_status"
                               value="${not empty user_atr?user_atr.status:user_status_ses}" class="form-control"
                               disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_balance}
                    </label>
                    <div class="col-md-10">
                        <input type="text" name="balance"
                               value="${not empty user_atr?user_atr.balance:balance_ses}" class="form-control"
                               disabled>
                    </div>
                </div>
                <div class="row">
                    <label class="col-sm-2 col-form-label mb-3">
                            ${user_discount}
                    </label>
                    <div class="col-md-10">
                        <input type="hidden" name="discount_id"
                               value="${not empty user_atr?user_atr.discountId:discount_id_ses}">
                        <input type="text" name="rate" value="${not empty rate_atr?rate_atr:rate_ses}%" class="form-control"
                               disabled>
                    </div>
                </div>

                <div class="row">
                    <label class="col-sm-4 col-form-label mb-3">
                        Enter password to update you personal data
                    </label>
                    <div class="col-md-6">
                        <input type="password" minlength="4" maxlength="12" name="password" id="pass"
                               pattern="[\da-zA-Z\-!«»#\$%&'\(\)\*\+,\./:;<=>\?@_`\{\|\}~]+"
                               required oninvalid="this.setCustomValidity('${password_rules} \'')"
                               class="form-control">
                    </div>
                    <label class="col-sm-2 col-form-label mb-3">
                        <a class="link-secondary text-decoration-none" id="show">
                                ${show_password}
                        </a>
                    </label>
                </div>
                <div class="row">
                    <div class="col text-danger">
                        <c:if test="${not empty wrong_password_ses}">
                            ${incorrect_password}
                        </c:if>
                    </div>
                </div>

                <div class="container text-center">
                    <button type="submit" class="btn btn-secondary">
                            ${update}
                    </button>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<footer>
    <jsp:include page="../../footer/footer.jsp"/>
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
