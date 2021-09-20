class Computer
{
    constructor(model, producer, price) 
    {
        this.model = model;
        this.producer = producer;
        this.price = price;
    }
}

// Создание компьютера:
//   1. Ввод полей 
//   2. Создание объекта на основе введенных данных
// @return - объект класса Computer
function createComputer() 
{
    const model    = prompt('Введите модель компьютера: ')
    const producer = prompt('Введите производителя компьютера')
    const price    = prompt('Введите цену компьютера')

    // Проверка поля price на содержание только цифр 
    // при помощи регулярного выражения
    if (!/^\d+$/.test(price))
    {
        alert('При создания объекта произошла ошибка!');
        return;
    }

    return new Computer(model, producer, price);
}

// Добавление html элемента с созданным компьютером
function addNewComputer()
{
    var computer = createComputer();

    // Находим блочный элемент для вставки 
    var element = document.getElementsByClassName('result_wrapper');

    // После начала (открывающего тега) найденного элемента
    // вставляем элемент с описанием компьютера
    element[0].insertAdjacentHTML('afterBegin',
    '<div class="result_item">' +
        '<div class="result_img">' + 
            '<img src="/icons/computer.png" alt="computer" class="computer_img">' + 
        '</div>' +
        '<div class="result_text">' +
            'Model: ' + computer.model+ '<br>' +
            'Producer: ' + computer.producer + '<br>' +
            'Price: ' + computer.price + '<br>' +
        '</div>' +
    '</div>'
    );
}
