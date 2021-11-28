import threading
from threading import BoundedSemaphore


class Reader:
    """
    Класс читатель
    Читает из лог файла события воспроизводимые прибором
    """

    # Ограничение кол-во параллельно работающих чтецов
    # при помощи семафора
    readersPool: BoundedSemaphore = BoundedSemaphore(10)

    def __init__(self, path: str, text):
        """
        Конструтор с параметром
        :param path: путь к лог файлу для чтения
        """
        self.path = path
        self.text = text

    def read(self):
        """
        Метод для запуска отедльного потока
        Читаем последнюю строку из файла и сравниванием ее
        с предыдущей последней строкой.
        Если строки не совпадают, значит прибор находится в новом
        состоянии о чем читатель и сообщает
        :return:
        """
        lastLine = str()

        # Цикл пока в строке не встретится 'Завершение',
        # вне зависимости от успешного/аварийного прерывания работы приборы
        # работаем с поледними строками файла
        while not ("Завершение" in lastLine.split(" ")):
            # Обращение к файлу - разделяемому ресурсу
            # ограждается семафорами
            self.readersPool.acquire()
            with open(self.path, 'r') as file:
                line = file.readlines()[-1]
                if lastLine != line:
                    self.text.insert('end', line)
                    self.text.see('end')
                    lastLine = line

            self.readersPool.release()

    def run(self):
        """Запуск потока"""
        threading.Thread(target=self.read).start()
