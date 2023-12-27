#Тестовое задание по созданию фильтров для перелетов
"Исключите из тестового набора перелёты по следующим правилам (по каждому правилу нужен отдельный вывод списка перелётов):
Вылет до текущего момента времени.
Сегменты с датой прилёта раньше даты вылета.
Перелеты, где общее время, проведённое на земле, превышает два часа (время на земле — это интервал между прилётом одного сегмента и вылетом следующего за ним)"

В ветке dev находится реализация, выполненная в виде отдельных методов класса FlightFilterImpl с использованием stream().
Добавлен метод валидации, при этом возможна излишняя итерация по коллекции сегментов. Но учитывая что сегментов в одном перелете не может быть очень много, это не должно влиять на производительность.

Также в репозитории имеется Pull Request с альтернативной реализацией через отдельные классы.
Там используются циклы for, операторы if и каждая проверка на возможные ошибки производится не отдельно, а непосредственно в теле метода, что позитивно отразиться на производительности при работе с большими коллекциями объектов.
Методы могут выглядеть усложненными, возмона разбивка функционала на более мелкие и узкоспециализированные методы.
Класс FlightFilterChain создается на основе нескольких классов-фильтров, тем самым позводляет выполнить фильтрацию коллекции перелетов сразу по нескольким параметрам
