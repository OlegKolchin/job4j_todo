function getDay() {
    var days = ['Воскресенье','Понедельник','Вторник','Среда','Четверг','Пятница','Суббота'];
    var now = new Date();
    return days[now.getDay()];
}

function getMonth() {
    var months = ['Января','Февраля','Марта','Апреля','Мая','Июня',
        'Июля','Августа','Сентября','Октября','Ноября','Декабря'];
    var now = new Date();
    return months[now.getMonth()];

}

function setDay() {
    var day = getDay();
    var el = document.getElementById('day');
    el.innerText = day;
}

function setDate() {
    var now = new Date();
    var date = now.getDate() + ' ' + getMonth() + ' ' + now.getFullYear();
    var el = document.getElementById('date');
    el.innerText = date;
}