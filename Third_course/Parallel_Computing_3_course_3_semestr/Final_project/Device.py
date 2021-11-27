import random
import threading
import time
from threading import BoundedSemaphore

from Logger import Logger


class Device:
    devicesPool: BoundedSemaphore = BoundedSemaphore(10)

    event: dict = {
        100: 'Завершение работы',
        1: 'Начал измерение',
        2: 'Проводит измерение',
        3: 'Анализирует данные',
        4: 'Фиксирует данные',
        -1: 'Завершение с ошибкой'
    }

    def __init__(self, name):
        self.isWork = False
        self.name = name
        self.logger = Logger()
        self.logger.setLogFile("Logs/" + self.name)

    def work(self):
        self.isWork = True

        self.devicesPool.acquire()
        self.logger.debug(self.name + ": " + self.event[1])

        while self.isWork:
            rand = random.randint(-10, 120)

            if rand < 0:
                self.logger.debug(self.name + ": " + self.event[-1])
                self.isWork = False

            elif 0 < rand <= 25:
                self.logger.debug(self.name + ": " + self.event[1])

            elif 25 < rand <= 50:
                self.logger.debug(self.name + ": " + self.event[2])

            elif 50 < rand <= 75:
                self.logger.debug(self.name + ": " + self.event[3])

            elif 75 < rand <= 100:
                self.logger.debug(self.name + ": " + self.event[4])

            else:
                self.logger.debug(self.name + ": " + self.event[100])
                self.isWork = False

            time.sleep(random.randint(1, 5))

        self.devicesPool.release()
        print(self.name + " закончил")

    def run(self):
        threading.Thread(target=self.work).start()

