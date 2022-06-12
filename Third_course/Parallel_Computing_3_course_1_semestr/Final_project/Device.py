import random
import threading
import time
from threading import BoundedSemaphore

from Logger import Logger
from Reader import Reader


class Device:
    """
    Класс приборов, которые будут проводить измерения
    и регистрировать события в файлах при помощи логгеров
    :see Logger.py:
    """

    # Ограничение кол-во параллельно работающих приборов
    # при помощи семафора
    devicesPool: BoundedSemaphore = BoundedSemaphore(10)

    # словарь событий
    # {код события: словесное описание}
    event: dict = {
        100: 'Завершение работы',
        1:    'Начал измерение',
        2:    'Проводит измерение',
        3:    'Анализирует данные',
        4:    'Фиксирует данные',
        -1:   'Завершение с ошибкой',
        -100: 'Завершение вручную'
    }

    def __init__(self, name, outStream):
        """
        Конструтор с параметрами.
        При создании прибор находится в состоянии покоя
        Так же за прибором закрепляется логгер
        :param name: имя прибора
        """
        self.isWork = False
        self.name = name.split("/")[-1]
        self.logger = Logger(name)
        self.text = outStream

    def work(self):
        """
        Метод для запуска в отдельном потоке.
        Прибор переходит в состояние 'работы' и прибывает
        в нем вплоть до завершения или завершения с ошибкой
        :return:
        """
        self.isWork = True

        # Обращение к файлу - разделяемому ресурсу
        # ограждается семафорами
        self.devicesPool.acquire()
        self.logger.system(self.name.ljust(10) + ": " + self.event[1])
        self.devicesPool.release()

        # Так же запускаем поток с Читателем файла логгирования
        # Такой подход позволяет работать с прибором и читать лог
        # не зависимо от других приборов
        Reader("Logs/" + self.name + ".log", self.text).run()
        time.sleep(random.randint(1, 8))

        # Цикл имитации работы прибора
        while self.isWork:
            rand = random.randint(-10, 110)

            # Обращение к файлу - разделяемому ресурсу
            # ограждается семафорами
            self.devicesPool.acquire()

            if rand < 0:
                self.logger.error(self.name.ljust(10) + ": " + self.event[-1])
                self.isWork = False

            elif 0 < rand <= 33:
                self.logger.debug(self.name.ljust(10) + ": " + self.event[2])

            elif 33 < rand <= 66:
                self.logger.debug(self.name.ljust(10) + ": " + self.event[3])

            elif 66 < rand <= 100:
                self.logger.debug(self.name.ljust(10) + ": " + self.event[4])

            else:
                self.logger.system(self.name.ljust(10) + ": " + self.event[100])
                self.isWork = False

            self.devicesPool.release()

            time.sleep(random.randint(3, 15))

    def run(self):
        """Запуск потока"""
        if not self.isWork:
            threading.Thread(target=self.work).start()

    def turnOff(self):
        """
        Функция выключения прибора
        :return:
        """
        self.isWork = False
        self.logger.warn(self.name.ljust(10) + ": " + self.event[-100])
