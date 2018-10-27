<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:choose>
    <c:when test="${isAuthenticated}">
        <%@ include file="header.jsp" %>
    </c:when>
    <c:otherwise>
        <!DOCTYPE HTML>
        <html lang="en">
        <head>
            <meta charset="UTF-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
            <title>Welcome</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
                  integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
                  crossorigin="anonymous">
            <link href="${contextPath}/static/css/main.css" rel="stylesheet">
        </head>
        <body>
        <%@ include file="anonimousNavbar.jsp" %>
        <div class="container">
    </c:otherwise>
</c:choose>
<div class="col-xl-12 col-md-12">
    <div class="card hovercard">
        <div class="card-background">
            <img class="card-bkimg" alt="" src="${contextPath}/user/${targetUser.id}/image">
        </div>
        <div class="useravatar">
            <img alt="" src="${contextPath}/user/${targetUser.id}/image">
        </div>
        <div class="card-info"><span class="card-title">${targetUser.name} ${targetUser.surname}</span>
        </div>
    </div>
    <c:if test="${not targetUser.settings.nonFriendsBlock or isFriend}">
        <div class="shadow-sm p-3 mb-5 bg-white rounded">
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="info-tab" href="#info" role="tab" aria-controls="info">Info</a>
                </li>
                <c:if test="${targetUser.settings.giftsVisibility}">
                    <li class="nav-item">
                        <a class="nav-link" id="presents-tab" href="#presents" role="tab" aria-controls="presents">Recent
                            presents</a>
                    </li>
                </c:if>
                <c:if test="${targetUser.settings.groupVisibility}">
                    <li class="nav-item">
                        <a class="nav-link" id="groups-tab" href="#groups" role="tab" aria-controls="groups">Joined
                            groups</a>
                    </li>
                </c:if>
                <c:if test="${targetUser.settings.friendsVisibility}">
                    <li class="nav-item">
                        <a class="nav-link" id="friends-tab" href="#friends" role="tab" aria-controls="friends">
                            Friends</a>
                    </li>
                </c:if>
            </ul>

            <div class="tab-content" id="myTabContent">
                <div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="info-tab">
                    <c:if test="${targetUser.settings.ageVisibility}">
                        <div class="row parent-float">
                            <div class="col-md-3">Age</div>
                            <div class="col-md-3">
                                <p>${targetUser.age}</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${targetUser.settings.birthdayVisibility}">
                        <div class="row parent-float">
                            <div class="col-md-3">Birth date</div>
                            <div class="col-md-3">
                                <p>${targetUser.birthDate}</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${targetUser.settings.locationVisibility and not empty targetUser.location}">
                        <div class="row parent-float">
                            <div class="col-md-3">Location</div>
                            <div class="col-md-3">
                                <p>${targetUser.location}</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${not empty targetUser.patronymic}">
                        <div class="row">
                            <div class="col-md-3">Patronymic</div>
                            <div class="col-md-9">
                                <p>${targetUser.patronymic}</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${targetUser.settings.genderVisibility}">
                        <c:choose>
                            <c:when test="${targetUser.gender == 0}">
                                <div class="row">
                                    <div class="col-md-3">Gender</div>
                                    <div class="col-md-9">
                                        <p>Male</p>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="row">
                                    <div class="col-md-3">Gender</div>
                                    <div class="col-md-9">
                                        <p>Female</p>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </div>
                <c:if test="${targetUser.settings.giftsVisibility}">
                    <div class="tab-pane fade" id="presents" role="tabpanel" aria-labelledby="presents-tab">
                        <table class="table">
                            <tr>
                                <th>Photo</th>
                                <th>Signature</th>
                                <th>Date</th>
                                <th>Sender</th>
                            </tr>
                            <c:forEach items="${presents}" var="present">
                                <tr>
                                    <td>
                                        <img src="${contextPath}/present/${present.id}/image"
                                             class="rounded dialog-header-img">
                                    </td>
                                    <td>${present.signature}</td>
                                    <td>${present.date}</td>
                                    <td><a href="${contextPath}/user/${present.sender.id}">
                                            ${present.sender.name} ${present.sender.surname}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <c:if test="${isAuthenticated}">
                            <a class="btn btn-success" href="${contextPath}/present/list/${targetUser.id}">Show all
                                presents</a>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${targetUser.settings.groupVisibility}">
                    <div class="tab-pane fade" id="groups" role="tabpanel" aria-labelledby="groups-tab">
                        <table class="table">
                            <tr>
                                <th>Photo</th>
                                <th>Name</th>
                                <th>Creator</th>
                            </tr>
                            <c:forEach items="${groups}" var="group">
                                <tr>
                                    <td>
                                        <img src="${contextPath}/group/${group.id}/image"
                                             class="rounded dialog-header-img">
                                    </td>
                                    <td>${group.name}</td>
                                    <td><a href="${contextPath}/user/${group.creator.id}">
                                            ${group.creator.name} ${group.creator.surname}</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <c:if test="${isAuthenticated}">
                            <a class="btn btn-success" href="${contextPath}/user/${targetUser.id}/groups">Show all
                                groups</a>
                        </c:if>
                    </div>
                </c:if>
                <c:if test="${targetUser.settings.friendsVisibility}">
                    <div class="tab-pane fade" id="friends" role="tabpanel" aria-labelledby="friends-tab">
                        <table class="table">
                            <tr>
                                <th>Photo</th>
                                <th>Name</th>
                            </tr>
                            <c:forEach items="${friends}" var="friend">
                                <tr>
                                    <td>
                                        <a href="${contextPath}/user/${friend.id}">
                                            <img src="${contextPath}/user/${friend.id}/image"
                                                 class="rounded dialog-header-img" alt="Avatar">
                                        </a>
                                    </td>
                                    <td>${friend.name} ${friend.surname}</td>
                                </tr>
                            </c:forEach>
                        </table>
                        <c:if test="${isAuthenticated}">
                            <a class="btn btn-success" href="${contextPath}/user/${targetUser.id}/friends">Show all
                                friends</a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </c:if>
</div>
<c:if test="${not targetUser.settings.nonFriendsBlock or isFriend}">
    <c:forEach items="${posts}" var="wallMessage">
        <div class="shadow-sm p-3 mb-5 bg-white rounded">
            <tr>
                <td>${wallMessage.date}</td>
                <td>${wallMessage.text}</td>
            </tr>
        </div>
    </c:forEach>
</c:if>
<c:if test="${isAuthenticated}">
    <div class="btn-group-vertical right-sidebar shadow-sm" id="right-menu">
        <c:if test="${not targetUser.settings.nonFriendsBlock or isFriend}">
            <button type="button" class="btn btn-light btn-lg border border-secondary" data-toggle="modal"
                    data-target="#modal">
                Send message
            </button>
            <button type="button" class="btn btn-light btn-lg border border-secondary" data-toggle="modal"
                    data-target="#modal-present"> Send present
            </button>
        </c:if>
        <button type="button" class="btn btn-light btn-lg border border-secondary" id="add-friend"
                onclick=addFriend("${targetUser.id}");> Add friend
        </button>
        <button type="button" class="btn btn-light btn-lg border border-secondary d-none" id="delete-friend"
                onclick=deleteFriend("${targetUser.id}");> delete friend
        </button>
    </div>
    <var id="isFriend" class="d-none">${isFriend}</var>
    <c:set var="recipient" value="${targetUser.id}"/>
    <%@ include file="messageForm.jsp" %>
    <%@ include file="presentForm.jsp" %>
</c:if>
<%@ include file="footer.jsp" %>
<script>
    var baseUrl = window.location.origin;
    var fileArray = [];

    $(document).ready(function () {
        var isFriend = $("#isFriend").text();
        if (isFriend == "true") {
            hideElement("#add-friend");
            showElement("#delete-friend");
        }
    });

    function sendForm(recipientId) {
        var formData = new FormData($("#message-form")[0]);
        formData['files'] = fileArray;
        $.ajax({
            url: baseUrl + "/message/send/" + recipientId,
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            success: function () {
                $('#modal').modal('hide');
                $('form').trigger('reset');
                $("#files-wrapper").empty();
                console.log("success");
            },
            error: function () {
                console.log("error");
            }
        });
    }

    $("#inputFile").change(function () {
        $("#files-wrapper").empty();
        var files = $("#inputFile").prop('files');
        fileArray = fillArray(files);
        var element;
        for (var i = 0; i < files.length; i++) {
            element = '<div class="added-file" onclick="deleteFile(' + i + ',this);">' +
                '<img src="https://upload.wikimedia.org/wikipedia/commons/0/0c/File_alt_font_awesome.svg">' +
                '<p>' + files[i].name + '</p></div>';
            $("#files-wrapper").append(element);
        }
    });

    function deleteFile(index, elem) {
        var files = $("#inputFile").prop('files');
        var file;
        for (var i = 0; i < files.length; i++) {
            if (i == index) {
                file = files[i];
                //delete fileArray[getElementIndex(file)];
                fileArray.splice(getElementIndex(file), 1);
            }
        }
        elem.remove();
    }

    function fillArray(elem) {
        var temp = [];
        for (var i = 0; i < elem.length; i++) {
            temp.push(elem[i]);
        }
        return temp;
    }

    function getElementIndex(elem) {
        for (var i = 0; i < fileArray.length; i++) {
            if (fileArray[i] == elem) return i;
        }
        return -1;
    }
</script>
</body>
</html>