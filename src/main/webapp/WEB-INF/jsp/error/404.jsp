<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>404 error</title>
    <link href="${contextPath}/static/css/errors.css" rel="stylesheet">
</head>
<body>
<div class="error">
    <h1>404</h1>
    <h2>Page not found! <a href="${contextPath}/">Go to the main page</a></h2>
</div>
</body>
</html>
