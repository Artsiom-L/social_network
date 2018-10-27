<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>403 error</title>
    <link href="${contextPath}/static/css/errors.css" rel="stylesheet">
</head>
<body>
<div class="error">
    <h1>403</h1>
    <h2>Access denied <br/>
        Sorry, but you don't have permission to view the requested content.</h2>
    <p>You can:
        <ul>
            <li><a href="${contextPath}/login">Go to sign in page</a></li>
            <li><a href="${contextPath}/registration">Go to registration page</a></li>
        </ul>
    </p>
</div>
</body>
</html>
