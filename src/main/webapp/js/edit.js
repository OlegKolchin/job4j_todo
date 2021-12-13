function addItem() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/task.do',
        data: {
            description: $('#description').val(),
            email: sessionStorage.getItem('user_email'),
            categories: getCategories()
        }
    }).done(function () {
        window.location.reload();
    }).fail(function (err) {
        alert('Выберите категорию!')
        console.log(err);
    });
}

function getCategories() {
    var rsl = '';
    var checkboxes = document.querySelectorAll('input[type=checkbox]:checked');
    var temp = [];
    for (var i of checkboxes) {
        if (i.id.includes('multiselect')) {
            temp.push(i);
        }
    }
    for (var i of temp) {
        rsl = rsl + i.value;
    }
    return rsl;
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

