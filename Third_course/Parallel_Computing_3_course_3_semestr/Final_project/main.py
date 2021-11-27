from Device import Device

if __name__ == '__main__':
    devices = list()

    for i in range(10):
        devices.append(Device(f"Прибор{i + 1}"))
        print(devices[i].name)

    for i in range(10):
        devices[i].run()
