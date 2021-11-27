import tkinter as tk
from tkinter import *

from Device import Device

devices = list()
readers = dict()
logDirectory = "Logs/"

count = 10


def onAllDevices():
    for el in range(count):
        devices[el].run()


def offAllDevices():
    for el in range(count):
        devices[el].turnOff()


if __name__ == '__main__':

    win = tk.Tk()
    win.title('Вар 8. Распредленное файловое хранилище')
    win.geometry("980x590+200+50")
    win.resizable(False, False)

    text = tk.Text(win, height=31, width=84, bg='black',
                   fg='white',
                   wrap=WORD,
                   insertbackground='white',
                   relief=RIDGE,
                   border=5,
                   )

    text.insert(1.0,
                "hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello hello "
                "hello hello ")

    text.grid(column=5, row=0, rowspan=100)

    scrollBar = tk.Scrollbar(command=text.yview)
    text.config(yscrollcommand=scrollBar.set)
    scrollBar.grid(column=6, row=0, rowspan=15, sticky=N + S, pady=20)

    for i in range(count):
        devices.append(Device(f"{logDirectory}Прибор{i + 1}", text))

    for i in range(devices.__len__()):
        label = tk.Label(win, text=f"{devices[i].name}:", height=3)
        label.grid(row=i, column=0, columnspan=2, padx=10)

        buttonOn = tk.Button(win, text='вкл', bg='green',
                             command=devices[i].run)
        buttonOff = tk.Button(win, text='офф', bg='red',
                              command=devices[i].turnOff)

        buttonOn.grid(row=i, column=3)
        buttonOff.grid(row=i, column=4)

    buttonOnAll = tk.Button(win, text='вкл всё', bg='green',
                            command=onAllDevices)

    buttonOffAll = tk.Button(win, text='выкл всё', bg='red',
                             command=offAllDevices)

    buttonOnAll.grid(row=11, column=3)
    buttonOffAll.grid(row=11, column=4)

    # textLabel = tk.Label(win, height=32, width=150, bg='black',
    #                      text="hello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello\nhello",
    #                      fg='white',
    #                      anchor='sw')
    # textLabel.grid(column=5, row=0, rowspan=100)


    win.mainloop()
