
$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/task.do',
        dataType: 'json'
    }).done(function (data) {
        for (var item of data) {
            var checked = '';
            if (item.done) {
                checked = 'checked';
            }
            $('.card-body').append($('<div class="form-check mb-3">\n' +
                '<input class="form-check-input" id="' + item.id.toString() + '"' + 'type="checkbox"' + checked + ' onchange="editItem(this)"' + '>\n' +
                '<label class="form-check-label" for="' + item.id.toString() + '"' + '><span class="fst-italic pl-1">' + item.description + ' ' + item.user.name  + '</span></label>\n' +
                '</div>'));
        };

        hideEl();
        setDay();
        setDate();
        auth();
    }).fail(function (err) {
        console.log(err);
    });
});

function hideEl() {
    var elements = document.getElementsByClassName('form-check-input');
    for (var i = 0; i < elements.length; i++) {
        var el = elements[i];
        if (el.checked) {
            $('#' + el.id).parent().hide();
        }
    }
}

function showEl() {
    var elements = document.getElementsByClassName('form-check-input');
    for (var i = 0; i < elements.length; i++) {
        var el = elements[i];
        $('#' + el.id).parent().show();
    }
}

function showSwitch() {
    if ($('#showAll').is(":checked")) {
        showEl();
    } else {
        hideEl();
    }
}

function logout() {
    sessionStorage.clear();
    location.href = 'http://localhost:8080/todo/';
    $('#upperMenu').replaceWith('<div id="upperMenu" class="input-group-append" style="position: absolute; top: 10px; right: 10px;">\n' +
        '<button class="btn btn-outline-secondary" type="button" id="button-login" onclick="redirect()">Войти</button>\n' +
        '</div>');
}

function auth() {
    var user = sessionStorage.getItem('user_name');
    if (user != null) {
        $('#upperMenu').replaceWith('<div id="upperMenu" class="input-group-append" style="position: absolute; top: 10px; right: 10px;">\n' +
            '<button class="btn btn-outline-secondary" type="button" id="button-login" onclick="logout()">Выйти</button>\n' +
            '</div>\n' +
            '<div id="upperMenu2" class="input-group-append" style="position: absolute; top: 10px; right: 85px;">\n' +
            '<button class="btn btn-outline-secondary" type="button" id="button-logout" disabled>' + user + '</button>\n' +
            '</div>')
    }
}