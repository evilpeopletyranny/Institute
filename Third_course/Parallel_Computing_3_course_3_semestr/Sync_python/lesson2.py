# Потоки - демоны
import threading
import time


def get_data(data):
    while True:
        print(f"[{threading.current_thread().name}] - {data}")
        time.sleep(1)


# Поток демон работает вместе с главным потоком и завершает работу с ним
thr = threading.Thread(target=get_data, args=(str(time.time()), ), daemon=True)
thr.start()
time.sleep(1)
print("finish")
