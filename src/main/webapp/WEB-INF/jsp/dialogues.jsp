<%@ include file="header.jsp" %>
<table class="table table-hover shadow-sm">
    <thead>
    <tr>
        <th scope="col">Img link</th>
        <th scope="col">Last message</th>
        <th scope="col">Date</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dialogues}" var="entry">
        <tr class="my-row">
            <th onclick="getDialog(${entry.key.id});">
                <a href="${contextPath}/user/${entry.key.id}">
                <img src="${contextPath}/user/${entry.key.id}/image" class="rounded my-thumbnail"></a>
            </th>
            <td class="my-message" onclick="getDialog(${entry.key.id});">
                <h6>${entry.key.name} ${entry.key.surname} (${entry.key.username})</h6>
                <p>${entry.value.text}</p>
            </td>
            <td>
                <button type="button" class="close" aria-label="Close" onclick="deleteDialog(${entry.key.id})"
                style="z-index: 100">
                    <span aria-hidden="true">&times;</span>
                </button>
                    ${entry.value.date}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="footer.jsp" %>
</body>
</html>