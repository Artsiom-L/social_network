<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Welcome</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css"
          integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">
    <link href="${contextPath}/static/css/main.css" rel="stylesheet">
</head>
<body>
<%@ include file="anonimousNavbar.jsp" %>
<div class="container">
    <form method="POST" action="${contextPath}/login" class="form-signin text-center">
        <h2 class="form-heading mb-3 font-weight-normal">Sign in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="username" type="text" class="form-control" placeholder="Username"
                   autofocus="true" required/>
            <input name="password" type="password" class="form-control" placeholder="Password" required/>
            <span>${error}</span>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign In</button>
            <h4 class="text-center">
                <a href="${contextPath}/registration">Create an account</a></h4>
        </div>
    </form>
</div>
</body>
</html>