function reg() {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/reg.do',
        data: {
            name: $('#name').val(),
            email: $('#email').val(),
            password: $('#password').val(),
        }
    }).done(function () {
        redirect();
    }).fail(function (err) {
        console.log(err);
    });
}

function validate() {
    var name = $('#name').val();
    var email = $('#email').val();
    var password = $('#password').val();
    if (name == '' || email == '' || password == "") {
        alert('Заполните все поля!');
        return false;
    }
    return true;
}

function redirect() {
    window.location.href = 'http://localhost:8080/todo/login.html';
}

function redirectIndex() {
    window.location.href = 'http://localhost:8080/todo/';
}
