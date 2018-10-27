<%@ include file="header.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading"><h1>Group List</h1></div>
    <table class="table">
        <tr>
            <th>Photo</th>
            <th>Name</th>
            <th>Creator</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${groups}" var="group">
            <tr>
                <td>
                    <a href="${contextPath}/group/${group.id}">
                        <img src="${contextPath}/group/${group.id}/image" class="group-image">
                    </a>
                </td>
                <td>${group.name}</td>
                <td><a href="${contextPath}/person/${group.creator.id}">
                        ${group.creator.name} ${group.creator.surname}</a>
                </td>
                <td>
                    <button type="button" class="btn btn-light btn-lg border border-secondary"
                            id="join-btn-${group.id}" onclick=joinGroup("${group.id}");> Join group
                    </button>
                    <button type="button" class="btn btn-light btn-lg border border-secondary d-none"
                            id="leave-btn-${group.id}" onclick=leaveGroup("${group.id}");> Leave group
                    </button>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="btn-group-vertical right-sidebar shadow-sm" id="right-menu">
    <button type="button" class="btn btn-light btn-lg border border-secondary" data-toggle="modal"
            data-target="#modal">
        Create group
    </button>
</div>
<%@ include file="groupForm.jsp" %>
<%@ include file="footer.jsp" %>
<script>
    $(document).ready(function () {
        $.ajax({
            url: "/group/subscribed",
            type: "GET",
            processData: true,
            contentType: 'json',
            cache: false,
            success: function (list) {
                console.log(list);
                for (var i = 0; i < list.length; i++) {
                    hideElement("#join-btn-" + list[i]);
                    showElement("#leave-btn-" + list[i]);
                }
            },
            error: function () {
                console.log("error");
            }
        });
    });
</script>
</body>
</html>
