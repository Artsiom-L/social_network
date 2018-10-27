<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <form:form method="POST" action="/registration" modelAttribute="registrationDto" class="form-registration"
               enctype="multipart/form-data">
        <h2 class="form-signin-heading">Create your account</h2>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="Username" required="required"
                            autofocus="true"/>
                <form:errors path="username"/>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="Password"
                            required="required"/>
                <form:errors path="password"/>
            </div>
        </spring:bind>

        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" class="form-control" required="required"
                            placeholder="Confirm your password"/>
                <form:errors path="passwordConfirm"/>
            </div>
        </spring:bind>

        <spring:bind path="birthDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">

                <form:input type="date" path="birthDate" class="form-control" required="required"/>
                <form:errors path="birthDate"/>
            </div>
        </spring:bind>

        <spring:bind path="gender">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                Male:<form:radiobutton path="gender" value="0" class="radio-inline"/>
                Female:<form:radiobutton path="gender" value="1" class="radio-inline"/>
                <form:errors path="gender"/>
            </div>
        </spring:bind>

        <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="name" class="form-control" required="required"
                            placeholder="Enter your name"/>
                <form:errors path="name"/>
            </div>
        </spring:bind>

        <spring:bind path="surname">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="surname" class="form-control" required="required"
                            placeholder="Enter your surname"/>
                <form:errors path="surname"/>
            </div>
        </spring:bind>

        <spring:bind path="patronymic">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="patronymic" class="form-control"
                            placeholder="Enter your patronymic"/>
                <form:errors path="patronymic"/>
            </div>
        </spring:bind>

        <spring:bind path="location">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="location" class="form-control"
                            placeholder="Enter your location"/>
                <form:errors path="location"/>
            </div>
        </spring:bind>

        <spring:bind path="image">
            <div class="form-group">
                <label for="image">Image</label>
                <input type="file" id="image" name="image" class="form-control-file"/>
            </div>
        </spring:bind>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>
</div>
</body>
</html>