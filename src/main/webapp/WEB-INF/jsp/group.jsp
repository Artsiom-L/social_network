<%@ include file="header.jsp" %>

<div class="card hovercard">
    <div class="card-background">
        <img class="card-bkimg" alt="" src="${contextPath}/group/${group.id}/image">
    </div>
    <div class="useravatar">
        <img alt="" src="${contextPath}/group/${group.id}/image">
    </div>
    <div class="card-info"><span class="card-title">${group.name}</span>
    </div>
</div>
<form id="message-form" method="POST" action="${contextPath}/post/${group.id}">
    <div class="form-group">
        <textarea class="form-control" id="textArea" rows="3" name="text"></textarea>
    </div>
    <div class="form-group">
        <button type="button" class="btn btn-primary dialog-button" onclick="sendGroupMessage(${group.id});">
            Submit</button>
    </div>
</form>
<br/><br/>
<c:forEach items="${messages}" var="message">
    <div class="shadow-sm p-3 mb-5 bg-white rounded">
        <tr>
            <td>${message.date}</td>
            <td><a href="${contextPath}/user/${message.sender.id}">
                    ${message.sender.name} ${message.sender.surname}</a>
            </td>
            <td>${message.text}</td>
        </tr>
    </div>
</c:forEach>
<%@ include file="footer.jsp" %>
</body>
</html>