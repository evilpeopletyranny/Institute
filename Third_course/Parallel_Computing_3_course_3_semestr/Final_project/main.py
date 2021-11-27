from Device import Device
from Reader import Reader

if __name__ == '__main__':
    devices = list()
    readers = dict()
    logDirectory = "Logs/"

    count = 10

    for i in range(count):
        devices.append(Device(f"{logDirectory}Прибор{i + 1}"))
        readers[devices[i].name] = Reader(f"{logDirectory}Прибор{i + 1}.log")

    for i in range(count):
        devices[i].run()

    # time.sleep(1)
    # for i in range(count):
    #     readers.get(devices[i].name).run()

    #
    # while devices:
    #     for device in devices:
    #         if device.isWork:
    #             devices.remove(device)
    #             readers.get(device.name).run()
    #             readers.pop(device.name)
    #             count -= 1

    # while count > 0:
    #     for device in devices:
    #         if device.isWork and (readers.get(device.name) is None):
    #             devices.remove(device)
    #             readers[device.name] = Reader(logDirectory + device.name + ".log")
    #             readers.get(device.name).run()
    #             count -= 1
