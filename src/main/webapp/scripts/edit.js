function addItem() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/task.do',
        data: {
            description: $('#description').val(),
            email: sessionStorage.getItem('user_email')
        }
    }).done(function () {
        window.location.reload();
    }).fail(function (err) {
        console.log(err);
    });
}

function editItem(obj) {
    var id = obj.id;
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/edit.do',
        data: {
            id: id
        }
    }).done(function () {
        if (!$('#' + 'showAll').is(":checked")) {
            $('#' + id).parent().hide();
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function validate() {
    var description = $('#description').val();
    if (description == '') {
        alert('Введите текст!');
        return false;
    }
    return true;
}

