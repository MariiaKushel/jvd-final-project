<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form name="RegistrationForm" method="post" action="../controller">
    ${param.wrong_data_message}<br/>
    <input type="hidden" name="command" value="registration"/>
    <input type="hidden" name="role" value="CLIENT"/>
    <input type="hidden" name="status" value="NEW"/>
    <input type="hidden" name="balance" value="0"/>
    <input type="hidden" name="discount_id" value="1"/>
    Login:<br/>
    <input type="text" name="login" value=""/><br/>
    Password:<br/>
    <input type="text" name="password" value=""/><br/>
    Phone number:<br/>
    <input type="text" name="phone_number" value=""/><br/>
    Name:<br/>
    <input type="text" name="name" value=""/><br/><br/>
    <input type="submit" value="ok"/><br/>
    <a href="../controller?command=go_to_welcome_page">Back to welcome page</a>
</form>
</body>
</html>
