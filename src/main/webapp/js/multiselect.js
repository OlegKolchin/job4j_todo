$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/cat',
        dataType: 'json'
    }).done(function (data) {
        for (var category of data) {
            $('#multipleOpt').append($('<option>', {
                value: category.id,
                text : category.name
            }));
        }
        $('#multipleOpt').multiselect('rebuild');
    }).fail(function (err) {
        console.log(err);
    });
});

$(document).ready(function() {
    $('#multipleOpt').multiselect;
});

$(document).ready(function() {
    $('#multipleOpt').multiselect({
        nonSelectedText: 'Выбрать категорию'
    });
});

