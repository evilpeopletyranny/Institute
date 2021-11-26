# Отличия Lock от RLock
# Синхронизация потково python

# Lock может разблокировать любой поток
# RLock может разблокировать только тот поток, который его заблокировал
import threading
import time

value = 0

# Обычный монитор
# Блокирует что то для всех потоков, кроме одного
locker = threading.Lock()
rlocker = threading.RLock()

def int_value():
    global value

    # while True:
    #     value += 1
    #     time.sleep(1)
    #     locker.acquire()
    #     print(value)
    #     locker.release()

    while True:
        with locker:
            value += 1
            time.sleep(1)
            print(value)


def inc_value2():
    print("Блокируем поток..")
    locker.acquire()
    print("Поток разблокирован..")


for _ in range(5):
    threading.Thread(target=int_value).start()
