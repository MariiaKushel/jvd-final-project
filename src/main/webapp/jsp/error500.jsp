
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName} <br/>
Status code: ${pageContext.errorData.statusCode} <br/>
Exception: ${pageContext.exception} <br/>
Message from exception: ${pageContext.exception.message} <br/>
<a href="../controller?command=go_to_welcome_page">Back to welcome page</a>
</body>
</html>
