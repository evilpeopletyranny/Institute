# Семафоры и Барьеры
# Семафор - монитор для n потоков
import random
import threading
import time
from threading import Thread, BoundedSemaphore

max_connections = 5
pool = BoundedSemaphore(value=max_connections)


def test():
    while True:
        with pool:
            print(threading.currentThread().name)
            time.sleep(random.randint(1, 5))


for i in range(10):
    Thread(target=test).start()
    time.sleep(5)
