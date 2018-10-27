<%@ include file="header.jsp" %>
<div class="panel panel-default">
    <div class="panel-heading"><h1>Present List</h1></div>
    <table class="table">
        <tr>
            <th>Photo</th>
            <th>Signature</th>
            <th>Date</th>
            <th>Sender</th>
        </tr>
        <c:forEach items="${presents}" var="present">
            <tr>
                <td><img src="${contextPath}/present/${present.id}/image" class="rounded dialog-header-img"></td>
                <td>${present.signature}</td>
                <td>${present.date}</td>
                <td><a href="${contextPath}/user/${present.sender.id}">
                        ${present.sender.name} ${present.sender.surname}</a>
                    <c:if test="${targetUserId eq user.id}">
                        <button type="button" class="close" aria-label="Close" onclick="deletePresent(${present.id})"
                                style="z-index: 100">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<%@ include file="footer.jsp" %>
<script>
    function deletePresent(id) {
        $.post(baseUrl + "/present/delete/" + id);
        location.reload(true);
    }
</script>
</body>
</html>
