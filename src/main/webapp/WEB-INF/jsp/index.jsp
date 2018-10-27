<%@ include file="header.jsp" %>
<div class="col-xl-12 col-md-12">
    <div class="card hovercard">
        <div class="card-background">
            <img class="card-bkimg" alt="" src="${contextPath}/user/${user.id}/image">
        </div>
        <div class="useravatar">
            <img alt="" src="${contextPath}/user/${user.id}/image">
        </div>
        <div class="card-info"><span class="card-title">${user.name} ${user.surname}</span>
        </div>
    </div>
    <div class="shadow-sm p-3 mb-5 bg-white rounded">
        <ul class="nav nav-tabs" id="myTab" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" id="info-tab" href="#info" role="tab" aria-controls="info">Info</a>
            </li>
            <c:if test="${user.settings.giftsVisibility}">
                <li class="nav-item">
                    <a class="nav-link" id="presents-tab" href="#presents" role="tab" aria-controls="presents">Recent
                        presents</a>
                </li>
            </c:if>
            <c:if test="${user.settings.groupVisibility}">
                <li class="nav-item">
                    <a class="nav-link" id="groups-tab" href="#groups" role="tab" aria-controls="groups">Joined
                        groups</a>
                </li>
            </c:if>
        </ul>

        <div class="tab-content" id="myTabContent">
            <div class="tab-pane fade show active" id="info" role="tabpanel" aria-labelledby="info-tab">
                <c:if test="${user.settings.ageVisibility}">
                    <div class="row parent-float">
                        <div class="col-md-3">Age</div>
                        <div class="col-md-3">
                            <p>${user.age}</p>
                        </div>
                    </div>
                </c:if>
                <c:if test="${user.settings.birthdayVisibility}">
                    <div class="row parent-float">
                        <div class="col-md-3">Birth date</div>
                        <div class="col-md-3">
                            <p>${user.birthDate}</p>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty user.location and user.settings.locationVisibility}">
                    <div class="row parent-float">
                        <div class="col-md-3">Location</div>
                        <div class="col-md-3">
                            <p>${user.location}</p>
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty user.patronymic}">
                    <div class="row">
                        <div class="col-md-3">Patronymic</div>
                        <div class="col-md-9">
                            <p>${user.patronymic}</p>
                        </div>
                    </div>
                </c:if>
                <c:if test="${user.settings.genderVisibility}">
                    <c:choose>
                        <c:when test="${user.gender == 0}">
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
            <c:if test="${user.settings.giftsVisibility}">
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
                    <a class="btn btn-success" href="${contextPath}/present/list/${user.id}">Show all my presents</a>
                </div>
            </c:if>
            <c:if test="${user.settings.groupVisibility}">
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
                    <a class="btn btn-success" href="${contextPath}/user/${user.id}/groups">Show all my
                            groups</a>
                </div>
            </c:if>
        </div>
    </div>
</div>
<form id="message-form" method="POST" action="${contextPath}/post">
    <div class="form-group">
        <textarea class="form-control" id="textArea" rows="3" name="text"></textarea>
    </div>
    <div class="form-group">
        <button type="button" class="btn btn-primary dialog-button" onclick="sendWallMessage();">Submit</button>
    </div>
</form>
<br/><br/>
<c:forEach items="${posts}" var="wallMessage">
    <div class="shadow-sm p-3 mb-5 bg-white rounded">
        <button type="button" class="close" aria-label="Close" onclick="deletePost(${wallMessage.id})">
            <span aria-hidden="true">&times;</span>
        </button>
        <tr>
            <td>${wallMessage.date}</td>
            <td>${wallMessage.text}</td>
        </tr>
    </div>
</c:forEach>
<%@ include file="footer.jsp" %>
</body>
</html>