s1 SEGMENT
ASSUME cs:s1, ds:s1, es:s1, ss:s1
org 100h;    резервирования места под PSP

;КОД ПРОГРАММЫ
print_str macro string;вывод сообщений на экран
	push ax
	push dx
	mov dx,offset string
	mov ah,9
	int 21h
	pop dx
	pop ax
endm

input_str macro string;ввод строки символов
	push ax
	push dx
	mov dx,offset string
	mov ah,0ah
	int 21h
	pop dx
	pop ax
endm

main:
	mov ax, 0003
	int 10h
	print_str mess1













mess1 db 10,13,'L','a','b','o''r','a','t','o','r','y',' ','w','o','r','k',' ','№','2',10,13,'$'
s1 ENDS
END main