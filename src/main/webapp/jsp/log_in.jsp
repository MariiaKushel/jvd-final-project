<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LogIn</title>
</head>
<body>
<form name ="LoginForm" method="post" action="../controller">
    ${param.wrong_login_password_message}<br/>
    <input type="hidden" name="command" value="log_in"/>
    Login (email):<br/>
    <input type="text" name="login" value=""/> <br/>
    Password:<br/>
    <input type="text" name="password" value=""/> <br/><br/>
    <input type="submit" value="ok"/> <br/>
    <a href="../controller?command=go_to_welcome_page">Back to welcome page</a>
</form>
</body>
</html>
