# Асинхронность на простых функциях
# Событийный цикл

# select это системная функция, которая нужна для мониторинга изменений
# состояний файловых объектов сокетов
# работает с каждый файлом который имеет .fileone() - номер файла - файловый дескриптор
from select import select

import socket
# socket = domain:port

# AF_INET - ip протокол 4 версии
# SOCK_STREAM - поддержка TCP
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

server_socket.bind(('localhost', 5000))
server_socket.listen()

to_monitor = []


def accept_collection(serv_socket):
    client_socket, address = serv_socket.accept()
    print('Connection from', address)

    to_monitor.append(client_socket)


def send_message(client_socket):
    print('Before .recv()')
    request = client_socket.recv(4096)

    if request:
        response = 'Hello world\n'.encode()
        client_socket.send(response)
    else:
        client_socket.close()


# Событийный цикл
def event_loop():
    while True:

        # select
        # 1ый список - файлы которые должны стать доступны для чтения
        # 2ой список - файлы которые должны стать доступны для записи
        # 3ий список - файлы от которых мы ждем ошибки
        ready_to_read, _, _ = select(to_monitor, [], [])    # read, write, errors

        # для сокетов доустпных для чтения
        # если сокет сервеный, то
        for sock in ready_to_read:
            if sock is server_socket:
                accept_collection(sock)
            else:
                send_message(sock)


if __name__ == '__main__':
    to_monitor.append(server_socket)

    event_loop()
    # accept_collection(server_socket)
