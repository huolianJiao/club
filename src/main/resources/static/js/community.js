function post() {
    let id = $('#questionId').val()
    let content = $('#commentContent').val()
    commentForTargetId(id, 1, content)
}

function comment(e) {
    let id = $(e).data("id")
    let content = $('#input-' + id).val()
    commentForTargetId(id, 2, content, e)
    $('#input-' + id).val("")
}

function commentForTargetId(targetId, type, content, e) {
    $.ajax({
        type: "POST",
        dataType: 'JSON',
        url: "/comment",
        async: false,
        data: JSON.stringify({
            parentId: targetId,
            content: content,
            type: type
        }),
        contentType: "application/json",
        success: function (response) {
            if (response.code == 200) {
                if (type == 1) {
                    $('#comment-div').hide()
                    window.location.reload()
                } else {
                    $('#sub-comment-refresh-' + targetId).load("/comment/" + targetId);
                }
            } else {
                if (response.code == 2003) {
                    let isAccepted = confirm(response.message);
                    if (isAccepted) {
                        localStorage.setItem("closable", true)
                        window.open(
                            "https://github.com/login/oauth/authorize?client_id=7ded7b95e18ef1e9f9d4&" +
                            "redirect_uri=http://localhost:8080/callback&scope=user&state=1"
                        )
                    }
                } else {
                    alert(response.message)
                }
            }
        },
        error: function (response) {

        }
    })
}

function collapseComments(e) {
    let id = $(e).data("id")
    if ($('#comment-' + id).hasClass("in")) {
        $('#comment-' + id).removeClass("in")
        $(e).removeClass("active")
    } else {
        $('#comment-' + id).addClass("in")
        $(e).addClass("active")
        $('#sub-comment-refresh-' + id).load("/comment/" + id);
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function selectTag(tag) {
    let value = $(tag).data("tag");
    let previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}
