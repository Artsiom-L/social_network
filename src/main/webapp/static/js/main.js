var baseUrl = window.location.origin;
var pageName = $("#pageName").text();

$("#search").keyup(function () {
    var input = $("#search").val();
    if (input.length >= 2) {
        $(".dropdown-item").remove();
        $.ajax({
            url: baseUrl + "/search",
            contentType: "text/plain",
            method: "POST",
            dataType: 'json',
            data: input,
            success: function (result) {
                if (result.length == 0) return;
                for (i = 0; i < result.length; i++) {
                    var element = "<a class='dropdown-item' href='" + baseUrl + "/user/" + result[i].id +
                        "'>" + result[i].name + " " + result[i].surname
                        + "</a>";
                    $("#dropdown-menu").append(element);
                }
            }
        });
    }
});

$("#search-button").on("click", function () {
    document.location.href = baseUrl + "/search?q=" + $("#search").val();
});


function deleteDialog(id) {
    $.post(baseUrl + "/message/dialog/delete/" + id);
    location.reload(true);
}

function getDialog(element) {
    location.href = baseUrl + "/message/dialog/" + element;
}

function joinGroup(groupId) {
    $.post("/group/subscribe/" + groupId);
    hideElement("#join-btn-" + groupId);
    showElement("#leave-btn-" + groupId);
}

function leaveGroup(groupId) {
    $.post("/group/leave/" + groupId);
    hideElement("#leave-btn-" + groupId);
    showElement("#join-btn-" + groupId);
}

function hideElement(elementId) {
    $(elementId).removeClass("d-block");
    $(elementId).addClass("d-none");
}

function showElement(elementId) {
    $(elementId).removeClass("d-none");
    $(elementId).addClass("d-block");
}

function createGroup() {
    var formData = new FormData($("#group-form")[0]);
    $.ajax({
        url: baseUrl + "/group/create",
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function () {
            $('#modal').modal('hide');
            $('form').trigger('reset');
            console.log("success creating group");
            location.reload();
        },
        error: function () {
            console.log("error creating group");
        }
    });
}

$('#myTab a').on('click', function (e) {
    e.preventDefault();
    $(this).tab('show');
});

function sendWallMessage() {
    var textArea = $("#textArea").val();
    $.ajax({
        url: "/post",
        contentType: "text/plain",
        method: "POST",
        dataType: 'json',
        data: textArea,
        success: function () {
            $('form').trigger('reset');
            location.reload();
            console.log(textArea);
        }
    });
}

function deletePost(postId) {
    $.post(baseUrl + "/post/delete/" + postId);
    location.reload(true);
}

function sendGroupMessage(groupId) {
    var textArea = $("#textArea").val();
    $.ajax({
        url: "/group/post/" + groupId,
        contentType: "text/plain",
        method: "POST",
        dataType: 'json',
        data: textArea,
        success: function () {
            $('form').trigger('reset');
            location.reload();
            console.log(textArea);
        }
    });
}


function addFriend(friendId) {
    $.ajax({
        url: baseUrl + "/add/friend/" + friendId,
        method: "POST",
        success: function () {
            hideElement("#add-friend");
            showElement("#delete-friend");
        }
    });
}

function deleteFriend(friendId) {
    $.ajax({
        url: baseUrl + "/delete/friend/" + friendId,
        method: "POST",
        success: function () {
            hideElement("#delete-friend");
            showElement("#add-friend");
        }
    });
}

function sendPresentForm(recipientId) {
    var formData = new FormData($("#present-form")[0]);
    $.ajax({
        url: baseUrl + "/present/send/" + recipientId,
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function () {
            $('#modal-present').modal('hide');
            $('form').trigger('reset');
            console.log("success");
        },
        error: function () {
            console.log("error");
        }
    });
}

function addCurrentFriend(friendId) {
    $.ajax({
        url: baseUrl + "/add/friend/" + friendId,
        method: "POST",
        success: function () {
            hideElement("#add-btn-" + friendId);
            showElement("#delete-btn-" + friendId);
        }
    });
}

function deleteFriendImmediately(friendId) {
    $.ajax({
        url: baseUrl + "/delete/friend/" + friendId,
        method: "POST",
        success: function () {
            if (pageName == "Friend") {
                location.reload();
            }
            hideElement("#delete-btn-" + friendId);
            showElement("#add-btn-" + friendId);
        }
    });
}