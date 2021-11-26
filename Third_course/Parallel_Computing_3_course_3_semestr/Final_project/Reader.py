import os


class Reader:
    def __init__(self, path):
        self.path = path

    def readLog(self):
        files = os.listdir(self.path)
        # for line in files:
        #     print(line)

        if files:
            file = open(self.path + files[0], 'r')
            print(file.readlines()[-1])
            file.close()

    # def checkUpdate(self):
    #     while True:
    #         timestamp = os.stat(self.path).st_mtime
    #         while 1:
    #             if timestamp != os.stat(self.path).st_mtime:
    #                 timestamp = os.stat(self.path).st_mtime
    #                 print('Файл изменён!')
    #                 self.readLog()
    #                 return
