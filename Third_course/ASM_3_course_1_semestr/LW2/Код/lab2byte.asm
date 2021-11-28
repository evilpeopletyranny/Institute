d1 SEGMENT byte public 'DATA'
	hello db 10,13, 'Laboratoty work #2', 10, 13, '$'
	enter_please db 10,13,'Input: ', 10, 13, '$'
	result db 10,13,'Output: ', 10,13,'$'

	str_in  dw 20, (?), 0Ah dup ("?")
	str_out dw 20 dup (' '), '$'
d1 ENDS

e1 SEGMENT byte public 'DATA'
		print macro string	;вывод строки
		push ax
		push dx
		mov dx,offset string
		mov ah,9
		int 21h
		pop dx
		pop ax
	endm
	
	input macro string	;ввод строки
		push ax
		push dx
		mov ah,0ah
		mov dx,offset string
		int 21h
		pop dx
		pop ax
	endm
e1 ENDS

s1 SEGMENT byte 'CODE'
assume cs:s1, ds:d1, es:e1, ss:stack1
_main:
	mov ax,d1
	mov ds, ax
	
	mov ax, e1
	mov es, ax
	
	;Начало программы
	print hello
	print enter_please
	
	input str_in
	
	mov si, offset str_in  + 1
	mov di, offset str_out
	
	mov cl, [si]
	ror cl, 1	;работаем со словами -> счетчик в 2 раза меньше
	inc si
	
	_loop:	;Внешний цикл на кол-во слов
	mov ax, [si]
	push cx		;прячем текущий счетчик в стэк
	mov cx, 16  ;заводим счетчик на 16 бит = 2 байта = слово
	xor bl, bl

_inner_loop:	;внутренний цикл на 16 бит
	ror ax,1
	adc bl,0
	loop _inner_loop
	
	cmp bx, 8		;если единиц 8 или больше
	je _next_val    ;то не записываем слово и переходим к следущему
	
	mov [di], ax	;иначе записываем слово
	inc di 
	inc di

_next_val:			;переход к следущему слову
	pop cx
	inc si 
	inc si
	loop _loop
	
	print result
	print str_out	;вывод полученного значения

	int 21h
	mov ax,4c00h
	int 21h
s1 ENDS

stack1 SEGMENT byte stack 'stack'
	dw 100 dup (?)
stack1 ends

END _main