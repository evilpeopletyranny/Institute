p1 macro f1		; Bывод сообщений на экран
	push ax
	push dx
	mov dx, offset f1
	mov ah, 09h
	mov ah, 09h
	int 21h
	pop dx
	pop ax
endm


p2 macro f2 	; Bвод строки символов
	push ax
	push dx
	mov dx, offset f2
	mov ah, 0Ah
	int 21h
	pop dx
	pop ax
endm


d1 SEGMENT para public 'data'

	mess1 db 'Input number: $'
	
	in_str label byte 	; Cтрока символов (не более 6)
	razmer db 7
	kol db (?)
	stroka db 7 dup (?)
	
	
	number dw 5 dup (0)   ; Mассив чисел
	
	siz dw 5              ; Kоличество чисел
	
	MinChet dw 0,0         ; Cумма положительных
	
	perevod db 10,13,'$'
	text_err1 db 'Input Error!', 10,10,'$'
	div_zero db 'Divition by zero!', 10,10,'$'
	messovf db 13,10,7,'Overflow!','$'
	MulNeg db 13,10,'Result: ','$'
	messageMinChet db 13,10,'Min of even: ','$'
	
	out_str db 6 dup (' '),'$'
	
	flag_err equ 1
	
d1 ENDS



c1 SEGMENT para public 'code'
ASSUME cs:c1, ds:d1, ss:st1

start:	
	mov ax, d1
	mov ds, ax
	
	;Установка текстового видеорежима, очистка экрана
	mov ax, 03h  	;ah=0 (номер функции),al=3 (номер режима)
	int 10h

    xor di,di
    mov cx, siz 	; В cx - размер массива
	
vvod:	
	push cx

m1:	
	p1 mess1    ; Вывод сообщения о вводе строки
	p2 in_str 	; Ввод числа в виде строки
	
	p1 perevod
	
	call diapazon	; Проверка диапазона вводимых чисел (-29999,+29999)
	
	cmp bh, flag_err  	; Сравним bh и flag_err
	je err1         	; Если равен -сообщение об ошибке ввода
	

	call dopust		; Проверка допустимости вводимых символов
	
	cmp bh, flag_err
	je err1
	
	call AscToBin 	; Преобразование строки в число
	inc di
	inc di
	pop cx
	loop vvod
	jmp m2
	
err1:   
	p1 text_err1	
	jmp endprog
	
; ->>>>>>>>>>>>>>>>>>>>>>>>> арифметическая обработка <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-----------------
m2:	
	mov cx, siz 	; В (cx) - размер массива
	mov si, offset number
	mov bx, 30000d
	
chet: 
	mov ax, [si]
	test ax, 01h
	jnz nechet
	cmp ax, bx
	jnl nechet
	mov bx, ax
nechet:
	inc si
	inc si
	loop chet
	mov MinChet, bx
	
	cmp bx, 30000d
	je err1
	
	p1 messageMinChet	
	
	mov ax, bx	
	call BinToAsc
	p1 out_str
	
	p1 perevod
		
	mov cx, siz 	; В (cx) - размер массива
	mov si, offset number
	mov ax, 01h
	xor dx, dx
	
minus:
		mov bx,[si]
		cmp bx, 00h
		jge positive
		imul bx
		jo overflow
positive:		
		inc si
		inc si
		loop minus
		
		mov bx, MinChet
		cmp bx, 00h
		je zero
		idiv bx
		
		p1 MulNeg
		
		call BinToAsc
		p1 out_str
		
		p1 perevod
		
		jmp endprog
		
zero:
		p1 div_zero
		jmp endprog
		
		
overflow:
		p1 messovf
		
		
endprog:
			mov ax, 4c00h
			int 21h
	
	
;****************************************************
;* Проверка диапазона вводимых чисел -29999,+29999	*
;* Аргументы:										*
;* 		Буфер ввода - stroka						*
;* 													*
;* Результат:										*
;* 		bh - флаг ошибки ввода						*
;****************************************************
	
DIAPAZON PROC
    xor bh, bh
	xor si, si
	
	cmp kol, 05h 	; Если ввели менее 5 символов, проверим их допустимость
	jb dop
		
	cmp stroka, 2dh 	; Eсли ввели 5 или более символов проверим является ли первый минусом
	jne plus 	; Eсли 1 символ не минус, проверим число символов
	
	cmp kol, 06h 	; Eсли первый - минус и символов меньше 6 проверим допустимость символов 
	jb dop        
	
	inc si		; Иначе проверим первую цифру
	jmp first

plus:   
	cmp kol,6	; Bведено 6 символов и первый - не минус 
	je error1	; Oшибка
	
first:  
	cmp stroka[si], 32h	; Cравним первый символ с '2'
	jna dop		; Eсли первый <= '2' - проверим допустимость символов
	
error1:
	mov bh, flag_err	; Иначе bh = flag_err
	
dop:	
	ret
DIAPAZON ENDP


;****************************************************
;* Проверка допустимости вводимых символов			*
;* Аргументы:										*
;* 		Буфер ввода - stroka						*
;*		si - номер символа в строке					*
;* 													*
;* Результат:										*
;* 		bh - флаг ошибки ввода						*
;****************************************************
DOPUST PROC

	xor bh, bh
    xor si, si
	xor ah, ah
	xor ch, ch
	
	mov cl, kol	; В (cl) количество введенных символов
	
m11:	
	mov al, [stroka + si] 	; B (al) - первый символ
	cmp al, 2dh	; Является ли символ минусом
	jne testdop	; Если не минус - проверка допустимости
	cmp si, 00h	; Если минус  - является ли он первым символом
	jne error2	; Если минус не первый - ошибка
	jmp m13
	
testdop:
	cmp al, 30h	;Является ли введенный символ цифрой
	jb error2
	cmp al, 39h
	ja error2
	
m13: 	
	inc si
	loop m11
	jmp m14
	
error2:	
	mov bh, flag_err	; При недопустимости символа bh = flag_err
	
m14:	
	ret
DOPUST ENDP

;****************************************************
;* ASCII to number									*
;* Аргументы:										*
;* 		B cx количество введенных символов			*
;*		B bx - номер символа начиная с последнего 	*
;* 													*
;* Результат:										*
;* 		Буфер чисел - number						*
;*		B di - номер числа в массиве				*
;****************************************************
AscToBin PROC
	xor ch, ch
	mov cl, kol
	xor bh, bh
	mov bl, cl
	dec bl
	mov si, 01h  ; В si вес разряда
	
n1:	
	mov al, [stroka + bx]
	xor ah, ah
	cmp al, 2dh	; Проверим знак числа
	je otr	; Eсли число отрицательное
	sub al,	30h
	mul si
	add [number + di], ax
	mov ax, si
	mov si, 10
	mul si
	mov si, ax
	dec bx
	loop n1
	jmp n2
otr:	
	neg [number + di]	; Представим отрицательное число в дополнительном коде
	
n2:	
	ret
AscToBin ENDP

;****************************************************
;* Number to ASCII									*
;* Аргументы:										*
;* 		Число передается через ax					*
;* 													*
;* Результат:										*
;* 		Буфер чисел - out_str						*
;****************************************************
BinToAsc PROC
	xor si, si
	add si, 05h
	mov bx, 0Ah
	push ax
	cmp ax, 00h
	jnl mm1
	neg ax
	
mm1:	
	cwd
	idiv bx
	add dl,30h
	mov [out_str + si], dl
	dec si
	cmp ax, 00h
	jne mm1
	pop ax
	cmp ax, 00h
	jge mm2
	mov [out_str + si], 2dh
	
mm2:	
	ret
BinToAsc ENDP
      
	  
c1 ENDS		
st1 SEGMENT para stack 'stack'
	dw 10 dup (?)
st1 ENDS

end start