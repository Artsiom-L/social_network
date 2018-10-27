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
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="${contextPath}/static/css/main.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggler"
            aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarToggler">
        <a class="navbar-brand" href="${contextPath}/">Social</a>
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a href="${contextPath}/list" class="nav-link">Person List</a>
            </li>
            <li class="nav-item">
                <a href="${contextPath}/settings" class="nav-link">Settings</a>
            </li>
            <c:if test="${user.settings.groupVisibility}">
                <li class="nav-item">
                    <a href="${contextPath}/group/list" class="nav-link">Groups</a>
                </li>
            </c:if>
            <li class="nav-item">
                <a href="${contextPath}/logout" class="nav-link">Logout</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <div class="dropdown">
                <input class="form-control mr-sm-2 dropdown-toggle" type="search" placeholder="Search" id="search"
                       aria-label="Search" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                       autocomplete="off" name="q">
                <div class="dropdown-menu" aria-labelledby="search" id="dropdown-menu"></div>
            </div>
            <button class="btn btn-outline-success my-2 my-sm-0" type="button" id="search-button">Search</button>
        </form>
    </div>
</nav>
<div class="btn-group-vertical shadow-sm left-sidebar">
    <c:if test="${user.settings.friendsVisibility}">
    <button type="button" class="btn btn-light btn-lg border border-secondary"
            onclick="window.location.href = window.location.origin + '/friends'">
        Friends
    </button>
    </c:if>
    <button type="button" class="btn btn-light btn-lg border border-secondary"
            onclick="window.location.href = window.location.origin + '/message/dialogues'">
        Messages
    </button>
    <button type="button" class="btn btn-light btn-lg border border-secondary"
            onclick="window.location.href = window.location.origin + '/support'">
        Support
    </button>
</div>
<div class="container">
