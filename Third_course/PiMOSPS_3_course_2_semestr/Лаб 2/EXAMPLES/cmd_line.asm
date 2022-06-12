;проверка ключа в командной строке, если без ключа - предупреждает, что нужен ключ 
;если /r или /g "заливает" экран красным или зеленым
; видеорежим 3 текстовый, 25 строк*18 столбцов 
;№№ строк 0-24 (0-18h) ; №№ столбцов 0-79 (0-4fh)
.model tiny
rect macro
	mov ah,6
	mov al,0
	mov cx,0
	mov dx,184fh	 ;  (0-24) (0-79)
	int 10h	
endm
.code
org 80h		;ком. строка по смещению 80h от начала PSP
cmd_len db ?	;длина ком. строки 
cmd_line db ?	;содержимое ком. строки 
org 100h
start:	mov ax,3			 ; видеорежим 25*80
	int 10h
	cld			; для строковой команды = увеличивать di
	mov cl,cmd_len		; счетчик для строковой команды=длине командной строки
	xor ch,ch
	mov di,offset cmd_line	; смещение командной строки в PSP
	mov al,' '
	repne scasb		;сканируем командную строку до 1-го пробела
	cmp byte ptr [di],'/'		;следующий байт это  / ?
	jne err2			;нет, переход для вывода сообщения о необходимости ключа
	inc di			;да, смотрим, что дальше
	cmp byte ptr [di], 'r'		;проверяем наличие ключа /r
	je red			;да, верный ключ /r - на обработку
	cmp byte ptr [di],'g'		;проверяем наличие ключа /g
	jne err1			;не тот ключ - сообщение об ошибке
	mov bh,20h		;атрибут=зеленый
	rect
	jmp pr_end
red:	mov bh,40h		;атрибут=красный
	rect
	jmp pr_end
err1:	mov dx,offset mess1
	mov ah,9
	int 21h
	jmp pr_end	
err2: 	mov dx,offset mess2
	mov ah,9
	int 21h		
pr_end:	mov ah,7			;getch
	int 21h
	mov ax,4c00h
	int 21h
mess1 db 'Need param: /r or /g!!!$'
mess2 db 'Need  / and param$'
end start
