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
                '<label class="form-check-label" for="' + item.id.toString() + '"' + '><span class="fst-italic pl-1">' + item.description +  '</span></label>\n' +
                '</div>'));
        };
        hideEl();
        setDay();
        setDate();
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

