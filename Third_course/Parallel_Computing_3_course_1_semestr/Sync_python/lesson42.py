# Local - локальные перемнные потоков
# Создает локальные переменные внутри потоков - странная штука, но раз она есть
# значит нужна
import threading

data = threading.local()


def get():
    print(data.value)


def t1():
    data.value = 111
    print("t1: ")
    get()


def t2():
    data.value = 222
    print("t2: ")
    get()


threading.Thread(target=t1).start()
threading.Thread(target=t2).start()