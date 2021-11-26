from Device import Device
from Writer import Writer
from Reader import Reader

if __name__ == '__main__':
    device = Device("Прибор №1")
    writer = Writer("Logs/")
    reader = Reader("Logs/")

    writer.writeLog(device, "начал работу")

    reader.readLog()
