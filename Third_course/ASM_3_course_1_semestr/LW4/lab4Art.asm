D SEGMENT para public 'DATA'
	bgColor equ 00110000b
	videoSeg equ 0b800h
	windowPosX dw 10
	windowPosY dw 5
	windowSizeX dw 30
	windowSizeY dw 10
	text db 'Example text$'
	header db 'Program1$'
D ENDS

C SEGMENT para public 'CODE'
assume cs:C, ds:D, ss:S
_convert proc; конвертация координат
	push bp
	mov bp, sp
	push ax
	push bx
	mov ax, [bp+6]
	mov bx, 0A0h
	mul bx
	mov bx, [bp+4]
	add bx, bx
	add ax, bx
	mov si, ax
	pop bx
	pop ax
	pop bp
	ret	4
ENDP

_draw proc; рисуем поле
	push windowPosY
	push windowPosX
	call _convert
	mov cx, windowSizeY
	_ass:
		push cx
		mov cx, windowSizeX
		_ass_vnutri:
			mov al, ' '
			mov ah, bgColor
			mov es:[si], ax
			add si, 2h
		loop _ass_vnutri
		add si, 160d
		sub si, windowSizeX
		sub si, windowSizeX
	pop cx
	loop _ass
	
	push windowPosY
	push windowPosX
	call _convert
	
	mov ah, bgColor ; начинаем рисовать верхнюю рамку
	mov al, 201d
	mov es:[si], ax ; рисуем левый верхний угол
	inc si
	inc si
	
	mov cx, windowSizeX
	sub cx, 2
	
	mov al, 205d
	_draw_upper_equals: ; рисуем верхний ряд =
	mov es:[si], ax
	inc si
	inc si
	loop _draw_upper_equals
	
	mov al, 187d
	mov es:[si], ax ; рисуем правый верхний угол
	
	mov cx, windowSizeY
	sub cx, 2
	
	mov al, 186d
	_draw_side_equals: ; рисуем боковые =
	add si, 160d
	sub si, windowSizeX
	sub si, windowSizeX
	inc si
	inc si
	mov es:[si], ax
	add si, windowSizeX
	add si, windowSizeX
	dec si
	dec si
	mov es:[si], ax
	loop _draw_side_equals
	
	add si, 160d	; рисуем левый нижний угол
	mov al, 188d
	mov es:[si], ax
	
	mov cx, windowSizeX
	sub cx, 2
	
	mov al, 205d
	_draw_bottom_equals: ; рисуем нижние =
	dec si
	dec si
	mov es:[si], ax
	loop _draw_bottom_equals
	
	dec si
	dec si
	mov al, 200d
	mov es:[si], ax	; рисуем правый нижний угол
	
	mov ah, 02h
	mov bh, 00
	mov dh, byte ptr windowPosY
	mov dl, byte ptr windowPosX ; ставим курсор в заголовок окна
	add dl, 5
	int 10h
	
	mov ah, 09h
	mov dx, offset header ; выводим заголовок
	int 21h
	
	mov ah, 02h
	mov bh, 00
	mov dh, byte ptr windowPosY
	inc dh
	mov dl, byte ptr windowPosX ; ставим курсор в наше окно
	inc dl
	int 10h
	
	mov ah, 09h
	mov dx, offset text ; выводим текст
	int 21h
	
	mov ah, 02h
	mov bh, 00
	mov dh, 25h
	inc dh
	mov dl, 80h ; прячем курсор
	inc dl
	int 10h
	
	ret
ENDP



_begin:
	mov ax, videoSeg
	mov es, ax
	mov ax, D
	mov ds, ax; установка видео-памяти
	
	_prostration:
	mov ah, 00h
	int 16h
	cmp ah, 10h
	je _das_ende
	
	cmp ah, 4Bh
	jne _tuda
		cmp windowPosX, 0
		jne _notX
			inc WindowPosX
		_notX:
		dec windowPosX; сдиг влево
	_tuda:
	cmp ah, 4Dh
	jne _suda
		mov ax, 80d
		sub ax, windowSizeX
		cmp ax, windowPosX
		jne _noX
			dec windowPosX
		_noX:
		inc windowPosX; сдиг вправо
	_suda:
	cmp ah, 48h
	jne _up
		cmp windowPosY, 0
		jne _notY
			inc windowPosY
		_notY:
		dec windowPosY; сдвиг вверх
	_up:
	cmp ah, 50h
	jne _down
		mov ax, 25d
		sub ax, windowSizeY
		cmp ax, windowPosY
		jne _noY
			dec windowPosY
		_noY: 
		inc windowPosY; cвиг вниз
	_down:
	
	mov ah, 0
	mov al, 3
	int 10h; смена видеорежима + очистка
	call _draw
	
	jmp _prostration
	_das_ende:
	mov ax, 4c00h
	int 21h 
C ENDS

S SEGMENT para stack 'STACK'
	dw 100 dup (?)
S ENDS
END _begin
