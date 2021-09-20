### RELEASE 1.0.0 (28.04.2021 21:30)

+ Добавлены DI GraphPrinter и GraphBuilder в класс App.
+ Подключена библиотека log4j для логгирования
+ Добавлено логгирование методов GraphBuilder'a при помощи Spring AOP, AspectJ и log4f

### betta.1(26.04.2021 23:05)

+ Добавлен класс Director, отвечающий за построение графа при помощи Билдера.
+ Добавление DI от graphPrinter и graphBuilder в App класс
+ Изменение GraphPrinter с использованием Stream API

### alpha.2(26.04.2021 12:58)

+ Добавлен интефрейс Builder и класс GraphBuilder, реализующий его. (паттерн Builder). В дальнейшем будет добавлен класс
  Director управлющий построением графа

### alpha.1(25.04.2021 20:23)

+ Программа перенесена на Maven и Spring
+ Изменена структра классов пакета Nodes - узлы графа.  
  Добавлены абстрактные классы ContainerNode - узел контейнер и Leaf - узел лист (конечный узел)
+ Изменены поля классов и порядок наследования, соотвествующее диаграмме  
  ![диаграмма измнений](https://github.com/progerSapog/Software-design-patterns-2-course-2-semestr/blob/main/%D0%9E%D1%84%D0%BE%D1%80%D0%BC%D0%BB%D0%B5%D0%BD%D0%B8%D0%B5/LW2/nodesChange.png)