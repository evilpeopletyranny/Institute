# Класс Timer в потоках
# запуск потоков по таймеру
import threading
import time


def test():
    while True:
        print("test")
        time.sleep(1)


# 10 - время ожидания перед вызовом
# test - функция которую надо вызвать
thr = threading.Timer(10, test)
thr.start()

while True:
    print("111")
    time.sleep(2)

    # отмена потока таймера
    thr.cancel()


