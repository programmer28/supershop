<jsp:useBean id="dpe_msg" scope="request" type="java.lang.String"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Data error</title>
</head>
<body>
<h1>Sorry< error occurred while processing your data</h1><br>
<h3>${dpe_msg}</h3><br>
<h3><a href="${pageContext.request.ContextPath}/index">Return to Main Page</a></h3>
</body>
</html>
