# Многопоточность на простых примерах
# Поток - нужная проверка в фоновом режиме, не нужна скорость
# Процесс - копия программы, запущенная отдельно
import threading
import time


def get_data(thr_name, data):
    while True:
        print(f"[{thr_name}] - {data}")
        time.sleep(3)

    # Запуска потока
    # target - функция для работы в потоке
    # args - аргументы функции


thread = threading.Thread(target=get_data, args=('Thread 1', str(time.time())), name="thr-1")
thread.start()

for i in range(30):
    print(f"current: {i}")
    time.sleep(2)

    if i % 10 == 0:
        print("active thread: ", threading.active_count())  # кол-во активных потоков
        print("enumerate: ", threading.enumerate())    # вывод всех потоков
        print("thr-1 is alive: ", thread.is_alive())    # проверка работает ли поток


# получение главного потока
print(threading.main_thread().name)
threading.main_thread().setName("MAIN")
print(threading.main_thread().name)

# для ожидания конца выполнения потока можно использовать JOIN() (как в любом ЯП)
