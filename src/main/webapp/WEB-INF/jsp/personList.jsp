<%@ include file="header.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading"><h1>${pageName} list</h1></div>
    <table class="table">
        <tr>
            <th>Image</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Patronymic</th>
            <th>BirthDate</th>
            <th>Location</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${people}" var="person">
            <tr>
                <td><a href="${contextPath}/user/${person.id}">
                    <img src="${contextPath}/user/${person.id}/image" class="rounded dialog-header-img"></a></td>
                <td><a href="${contextPath}/user/${person.id}">
                        ${person.name}</a>
                </td>
                <td><a href="${contextPath}/user/${person.id}">
                        ${person.surname}</a></td>
                <td>
                    <c:if test="${not empty person.patronymic}">
                        ${person.patronymic}
                    </c:if>
                </td>
                <td>
                    <c:if test="${person.settings.birthdayVisibility}">
                        ${person.birthDate}
                    </c:if>
                </td>
                <td>
                    <c:if test="${person.settings.locationVisibility}">
                        ${person.location}
                    </c:if>
                </td>
                <td>
                    <button type="button" class="btn btn-light btn-lg border border-secondary"
                            id="add-btn-${person.id}" onclick=addCurrentFriend("${person.id}");> Add friend
                    </button>
                    <button type="button" class="btn btn-light btn-lg border border-secondary d-none"
                            id="delete-btn-${person.id}" onclick=deleteFriendImmediately("${person.id}");> Delete friend
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="right-sidebar shadow-sm d-none filter" id="filtration">
    <h4>Age filter:</h4>
    <form id="filter-form" enctype="text/plain" method="get" action="${contextPath}/filter">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="prepend-from">From</span>
            </div>
            <input type="number" class="form-control" placeholder="Write age" aria-label="From" name="youngest"
                   aria-describedby="prepend-from" id="filter-from" value="0">
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="prepend-to">To</span>
            </div>
            <input type="number" class="form-control" placeholder="Write age" aria-label="To" name="eldest"
                   aria-describedby="prepend-to" id="filter-to" value="150">
        </div>
        <input type="text" class="d-none" id="hide-form-namepage">
        <button type="submit" class="btn btn-primary">Filter</button>
    </form>
</div>
<var id="pageName" class="d-none">${pageName}</var>
<%@ include file="footer.jsp" %>
<script>
    $(document).ready(function () {
        if (pageName == "Person" || pageName == "Friend") {
            showElement("#filtration");
        }
        if (pageName == "Friend") {
            $("#hide-form-namepage").attr("name", "friends");
        }
        $.ajax({
            url: "/user/friends",
            type: "GET",
            processData: true,
            contentType: 'json',
            cache: false,
            success: function (list) {
                for (var i = 0; i < list.length; i++) {
                    hideElement("#add-btn-" + list[i]);
                    showElement("#delete-btn-" + list[i]);
                }
            },
            error: function () {
                console.log("error getting friends");
            }
        });
    });
</script>
</body>
</html>
