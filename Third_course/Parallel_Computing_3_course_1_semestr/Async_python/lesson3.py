# Асинхронность на колбэках

# selectors платформонезависимая абстракция над select
# и аналогичными функциями
import selectors
import socket
# socket = domain:port

selector = selectors.DefaultSelector()


def server():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_socket.bind(('localhost', 5000))
    server_socket.listen()

    # регистрация файлового объекта
    # fileobject - объект
    # events - событие
    # data - любое изменение
    selector.register(fileobj=server_socket, events=selectors.EVENT_READ, data=accept_collection)


def accept_collection(serv_socket):
    client_socket, address = serv_socket.accept()
    print('Connection from', address)

    # регистрация файлового объекта
    # fileobj - объект
    # events - событие
    # data - любое изменение
    selector.register(fileobj=client_socket, events=selectors.EVENT_READ, data=send_message)


def send_message(client_socket):
    print('Before .recv()')
    request = client_socket.recv(4096)

    if request:
        response = 'Hello world\n'.encode()
        client_socket.send(response)
    else:
        # Снятие объекта с регистрации
        selector.unregister(client_socket)
        client_socket.close()


# Событийный цикл
def event_loop():
    while True:
        events = selector.select()  # (key, events)

        # SelectorKey - именованный кортеж
        # fileobg, events, data
        # по картежу на каждый зарегистрированный объект

        for key, _ in events:
            callback = key.data
            callback(key.fileobj)


if __name__ == '__main__':
    server()
    event_loop()
