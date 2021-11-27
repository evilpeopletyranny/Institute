import datetime
import os


class Logger:
    types = {
        'INFO': "INFO",
        'DEBUG': "DEBUG",
        'ERROR': "ERROR",
        'WARNING': "WARNING"
    }

    def __writeLog(self, message: str):
        time = datetime.datetime.now()
        timeStr = str(time.date()) + " " + str(time.time())

        if self.path is None:
            print(timeStr + message)
        else:
            file = open(self.path + ".log", "a")
            file.write(timeStr + " " + message + "\n")
            file.close()

    def __deleteOldLogs(self):
        deleteFlag: bool = False

        directory = self.path.split('/')[0]
        print(directory)

        for file in os.listdir(directory):
            if os.path.getsize(directory + "/" + file) > 1024:
                deleteFlag = True
                break

        if deleteFlag:
            for file in os.listdir(directory):
                os.remove(directory + "/" + file)

    def __init__(self):
        self.path = None

    def setLogFile(self, path: str):
        self.path = path
        self.__deleteOldLogs()

    def info(self, message: str):
        self.__writeLog(self.types['INFO'] + " " + message)

    def debug(self, message: str):
        self.__writeLog(self.types['DEBUG'] + " " + message)

    def error(self, message: str):
        self.__writeLog(self.types['ERROR'] + " " + message)

    def warn(self, message: str):
        self.__writeLog(self.types['WARNING'] + " " + message)
