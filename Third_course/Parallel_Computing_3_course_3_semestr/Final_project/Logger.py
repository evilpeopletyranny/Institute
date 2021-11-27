import datetime
import os


class Logger:
    """
    Логер, умеющий делать запись в файл.
    Использует для записи 'событий' прибора.

    :see Device.py
    """

    def __writeLog(self, message: str):
        """
        'Приватный' метод записи лога в файл.
        Вызывается в других функциях класса.

        :param message: сообщение для записи
        :return:
        """

        # Получение даты и времени
        time = datetime.datetime.now()
        timeStr = str(time.date()) + " " + str(time.time()).split(".")[0]

        with open(self.path, "a") as file:
            file.write(timeStr + " " + message + "\n")

    def __deleteOldLogs(self):
        """
         'Приватный' очистки 'тяжелых' log файлов.
         Если вес лога превышает 1Кб, то удаляем данный файл.
         Для записи будет создан новый файл.
        :return:
        """
        directory = self.path.split('/')[0]
        for file in os.listdir(directory):
            filePath = directory + "/" + file
            if os.path.getsize(filePath) > 1024:
                os.remove(filePath)

    def __init__(self, path: str):
        """
        Конструктор с параметром
        :param path: путь для записи лога
        """
        self.path = path + ".log"
        self.__deleteOldLogs()

    # Типичные уровни логирования
    def info(self, message: str):
        self.__writeLog('INFO' + " " + message)

    def debug(self, message: str):
        self.__writeLog('DEBUG' + " " + message)

    def error(self, message: str):
        self.__writeLog('ERROR' + " " + message)

    def warn(self, message: str):
        self.__writeLog('WARNING' + " " + message)
