import Device


class Writer:
    def __init__(self, path):
        self.path = path

    def writeLog(self, device: Device, event: str):
        file = open(self.path + device.name + ".log", "a")
        file.write(device.name + ": " + event + "\n")
        file.close()
