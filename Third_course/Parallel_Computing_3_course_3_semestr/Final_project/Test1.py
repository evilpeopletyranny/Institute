from tkinter import Label, Button, Tk

window = Tk()
window.title("Start/Stop Button")
window.geometry('200x100')

def clicked_rf1():
    if btn_rf1['text'] == "Start":
        btn_rf1.configure(text="Stop")
        lbl_rf1.configure(text="  ON  ", bg="green")
    else:
        btn_rf1.configure(text="Start")
        lbl_rf1.configure(text="  OFF ", bg="red")

btn_rf1 = Button(window, text="Start", command=clicked_rf1)
btn_rf1.grid(column=1, row=1)
lbl_rf1 = Label(window, text="  OFF ", bg="red")
lbl_rf1.grid(column=2, row=1)

window.mainloop()