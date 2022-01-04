<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%--<fmt:setLocale value="${sessionScope.locale}" scope="session"/>--%>
<fmt:setLocale value="en_US" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<html>
<head>
    <title><fmt:message key="title.create_new_account"/></title>
</head>
<body>
<form name="CreatNewAccountForm" method="post" action="../controller">
    ${param.wrong_data_message}<br/>
    <input type="hidden" name="command" value="create_new_account"/>
    <fmt:message key="field.email"/><br/>
    <input type="text" name="login" value=""/><br/>
    <fmt:message key="field.password"/><br/>
    <input type="text" name="password" value=""/><br/>
    <fmt:message key="field.phone_number"/><br/>
    <input type="text" name="phone_number" value=""/><br/>
    <fmt:message key="field.name"/><br/>
    <input type="text" name="name" value=""/><br/><br/>
    <input type="submit" value="<fmt:message key="button.confirm"/>"/> <br/>
    <a href="../controller?command=go_to_main_page"><fmt:message key="reference.back_to_main"/></a>
</form>
</body>
</html>
