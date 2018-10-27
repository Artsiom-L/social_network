<%@ include file="header.jsp" %>
<div id="header" class="shadow-sm">
    <div class="dialog-person"><a href="${contextPath}/user/${interlocutor.id}">
        <img src="${contextPath}/user/${interlocutor.id}/image" class="rounded dialog-header-img"></a>
        ${interlocutor.name} ${interlocutor.surname}</div>
    <div class="dialog-person dialog-person-right"><a href="${contextPath}/user/${user.id}">
        <img src="${contextPath}/user/${user.id}/image" class="rounded dialog-header-img"></a>
        ${user.name} ${user.surname}</div>
    </div>
<var id="interlocutorId" class="d-none">${interlocutor.id}</var>
<div id="dialog-wrapper" class="shadow-sm dialog-wrapper">
    <c:forEach items="${messages}" var="message">
        <c:choose>
            <c:when test="${interlocutor.id == message.sender.id}">
                <div class="dialog">
            </c:when>
            <c:otherwise>
                <div class="dialog dialog-right">
            </c:otherwise>
        </c:choose>
                <button type="button" class="close" aria-label="Close" onclick="deleteMessage(${message.id})">
                    <span aria-hidden="true">&times;</span>
                </button>
            <div class="dialog-header">${message.date}</div>
            <div class="dialog-body">
                <p>${message.text}</p>
                <c:forEach items="${message.attachments}" var="attachment">
                    <p><a href="${contextPath}/message/download/${message.id}/${attachment.id}"
                          class="btn btn-default">Link</a>
                    </p>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
</div>
<form id="message-form" method="POST" action="${contextPath}/message/send/${interlocutor.id}"
      enctype="multipart/form-data" >
    <div class="form-group">
        <textarea class="form-control" id="textArea" rows="3" name="text"></textarea>
    </div>
    <div class="form-group">
        <div class="custom-file dialog-file-input">
            <input type="file" class="custom-file-input" name="files"
                   id="formControlFile" multiple>
            <label class="custom-file-label" for="formControlFile">Choose file</label>
        </div>
        <!--<input type="file" class="form-control-file dialog-file-input" id="formControlFile" multiple>-->
        <button type="button" class="btn btn-primary dialog-button" onclick="sendForm(${interlocutor.id});">Submit</button>
    </div>
    <div id="files-wrapper"></div>
</form>
<%@ include file="footer.jsp" %>
<script>
    var baseUrl = window.location.origin;
    var fileArray = [];
    var interlocutorId = $("#interlocutorId").val();

    window.onload = scrollDown();

    function scrollDown() {
        document.getElementById('dialog-wrapper').scrollTop = 9999;
    }

    function deleteMessage(id) {
        $.post(baseUrl + "/message/delete/" + id);
        location.reload(true);
    }

    $("#formControlFile").change(function () {
        $("#files-wrapper").empty();
        var files = $("#formControlFile").prop('files');
        fileArray = fillArray(files);
        var element;
        for(var i = 0; i < files.length; i++) {
            element = '<div class="added-file" onclick="deleteFile(' + i + ',this);">' +
                '<img src="https://upload.wikimedia.org/wikipedia/commons/0/0c/File_alt_font_awesome.svg">' +
                '<p>' + files[i].name + '</p></div>';
            $("#files-wrapper").append(element);
        }
    });

    function deleteFile(index, elem) {
        var files = $("#formControlFile").prop('files');
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


    function sendForm(recipientId) {
        var formData = new FormData($("#message-form")[0]);
        formData['files'] = fileArray;
        var header = '<div class="dialog dialog-right">';
        var result;
        $.ajax({
            url: "/message/send/" + recipientId,
            type: "POST",
            data: formData,
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            success: function (data) {
                $('form').trigger('reset');
                $("#files-wrapper").empty();
                result = header +
                    '<button type="button" class="close" aria-label="Close" onclick="deleteMessage(' + data.id + ')">' +
                    '<span aria-hidden="true">&times;</span></button>' +
                    '<div class="dialog-header">' + data.date +'</div>\n' +
                    '<div class="dialog-body"><p>' + data.text + '</p>\n';
                if (data.attachments != null) {
                    for (var i = 0; i < data.attachments.length; i++) {
                        result = result + '<p><a href="' + baseUrl +'/message/download/' +
                        data.id + '/' + data.attachments[i].id + '" class="btn btn-default">Link</a></p>';
                    }
                }
                result = result +'</div></div>';
                $("#dialog-wrapper").append(result);
                console.log("success");
                scrollDown();
            },
            error: function () {
                console.log("error");
            }
        });
    }

    window.setInterval(getMessages, 6000);

    function getMessages(){
        $.ajax({
            url: "/message/missed",
            type: "GET",
            processData: true,
            dataType: "json",
            cache: false,
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var elem = data.pop();
                    var result, header;
                    header = '<div class="dialog">';
                    result = header +
                        '<button type="button" class="close" aria-label="Close" onclick="deleteMessage(' + elem.id + ')">' +
                        '<span aria-hidden="true">&times;</span></button>' +
                        '<div class="dialog-header">' + elem.date +'</div>\n' +
                        '<div class="dialog-body"><p>' + elem.text + '</p>\n';
                    if (elem.attachments != null) {
                        for (var i = 0; i < elem.attachments.length; i++) {
                            result = result + '<p><a href="' + baseUrl +'/message/download/' +
                                elem.id + '/' + elem.attachments[i].id + '" class="btn btn-default">Link</a></p>';
                        }
                    }
                    result = result + '</div></div>';
                    $("#dialog-wrapper").append(result);
                    scrollDown();
                }
            },
            error: function () {
                console.log("error getting messages");
            }
        });
    }
</script>
</body>
</html>