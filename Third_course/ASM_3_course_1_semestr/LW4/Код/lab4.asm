;Лабораторная работа №4
;Вариант 13 (Здадание из лабораторной №3):
;	Вычислить сумму положительных, произведение отрицательных и их разность,
;   а также определить минимальное и максимальное число.



d1 SEGMENT para public USE16 'data'
	titleBGColor     equ 00001110b	;черный фон  - желтые буквы
	startBGColor     equ 00001011b	;черный фон  - голубые буквы
	waitBgColor      equ 11110001b  ;серый  фон  - синие буквы (моргающие)
	mainBgColor      equ 00010010b  ;синий  фон  - зеленые буквы
	errorBgColor     equ 01000000b	;красный фон - черные буквы
	errorBgColorWait equ 11000000b	;красный фон - черные моргающие буквы
	resultBGColor    equ 01110001b	;серый фон   - синие буквы
	videoSeg         equ 0b800h
	
	;Давно в далекой далекой галактике....
	LongTimeAgo  db 'A long time ago in a galaxy far,',10, 24 dup (' '), 'far away...$'
	
	;Ассемблерные войны
	Assembler db 'ASSEMBLER$',10,13
	Separator db '===========$', 10, 13
	
	;введение с разнм кол-ом строк для построчного вывода
	FullIntroduction1  db 15 dup (' '),'Episode IV', '$'
	FullIntroduction2  db 15 dup (' '),'Episode IV', 10, 30 dup (' '), 'A NEW LOBORATOTY WORK', '$'
	FullIntroduction3  db 15 dup (' '),'Episode IV', 10, 30 dup (' '), 'A NEW LOBORATOTY WORK',10, 13, '$'
	FullIntroduction4  db 15 dup (' '),'Episode IV', 10, 30 dup (' '), 'A NEW LOBORATOTY WORK',10, 13, 10, 20 dup (' '), 'The assembly language laboratory work is', '$'
	FullIntroduction5  db 15 dup (' '),'Episode IV', 10, 30 dup (' '), 'A NEW LOBORATOTY WORK',10, 13, 10, 20 dup (' '), 'The assembly language laboratory work is',10, 20 dup(' '),'underway. Students make their first', '$'
	FullIntroduction6  db 15 dup (' '),'Episode IV', 10, 30 dup (' '), 'A NEW LOBORATOTY WORK',10, 13, 10, 20 dup (' '), 'The assembly language laboratory work is',10, 20 dup(' '),'underway. Students  make their first',10, 20 dup(' '),'successes in the battle with the evil', '$'
	FullIntroduction   db 15 dup (' '),'Episode IV', 10, 30 dup (' '), 'A NEW LOBORATOTY WORK',10, 13, 10, 20 dup (' '), 'The assembly language laboratory work is',10, 20 dup(' '),'underway. Students  make their first',10, 20 dup(' '),'successes in the battle with the Elena',10, 20 dup(' '),'Nikolaevna...', '$'
	
	countYStart db 24d	;окно встпуления - начальная строка
	countYEnd   db 57d	;окно встпуления - конечная  строка
	
	WaitToStartText     db 'Press Enter to start, F4 to exit.$'
	WaitToMoveWiindow   db 'Use the arrows to move window, press Enter to Start, F4 to exit.$'
	WaitAnyKeyToExit    db 'Press any key to exit.$'
	WaitToReloadOrExit1 db 'Press F5 to show credits, F4 to exit.$'
	WaitToExit          db 'F4 to exit.$'
	Result				db 'Results$'	
	
	credits db 17 dup (' '), 'Credits',10, 10, 23 dup (' '), 'idea:         Sapozhnikov Vladislav', 10, 23 dup (' '), 'director:     Sapozhnikov Vladislav', 10, 23 dup (' '), 'producer:     Sapozhnikov Vladislav', 10, 23 dup (' '), 'programmer:   Sapozhnikov Vladislav', 10,  10, 33 dup (' '), 'ZACHET POSTAVLEN', 10,13, '$' 
	
	mainWindowXStart db 6d
	mainWindowYStart db 7d
	mainWindowXEnd   db 55d
	mainWindowYEnd   db	13d
	
	coursorX db 0
	coursorY db 0
	
	inputError db 'Input Error!', 10,10,'$'
	overflow db 'Overflow!', 10,10,'$'
	sumPosText db  'Sum of positive elements:             ','$'
	mulNegText db  'Mul of negative element:              ','$'
	diffResText db 'Difference of sum and multiplication: ','$'
	maxElText db   'Max element:                          ','$'
	minElText db   'Min element:                          ','$'
	out_str db 6 dup (' '),'$'
	enter_please db 'Input number from -29999 to 29999: $'
	
	flag_err equ 1
	
	in_str label byte   	 ; Cтрока символов (не более 6)
	razmer db 7              ; Размер буфера (6 символов и знак)
	kol db (?)               ; Количество введеных символов
	stroka db 7 dup (?)      ; Буфер ввода чисел
	number dw 5 dup (0)  	 ; Mассив чисел
	
	sumPos dw 0              ; Сумма положительных чисел
	mulNeg dw 0              ; Произведение отрицательных
	diffRez dw 0             ; Разность суммы и произведения
	min dw  29999            ; Минимальный  элемент
	max dw -29999            ; Максимальный элемент
	siz dw 5              	 ; Kоличество чисел
d1 ENDS



st1 SEGMENT para stack USE16 'stack'
	dw 100 dup (?)
st1 ENDS



;Макрос вывода в окне
; string - текст для вывода
; row    - строка вывода
; column - колонка вывода 
printInWindow macro string, row, column
	push ax
	push dx
	
	mov ah,2
	mov dh,row
	mov dl,column
	mov bh,0
	int 10h
	
	mov ah, 09h
	mov dx, offset string
	int 21h
	
	pop dx
	pop ax
endm



;Макрос вывода сообщений на экран
; string - строка для вывода
print macro srting		
	push ax
	push dx
	mov dx, offset srting
	mov ah, 09h
	int 21h
	pop dx
	pop ax
endm


; Макрос вывода строки символов
; string - строка для ввода
input macro srting 	
	push ax 
	push dx
	mov dx, offset srting
	mov ah, 0Ah
	int 21h
	pop dx
	pop ax
endm



;Макрос ожидания при помощи функции 86h прерывания Int 15h
; time - время в миллисекундах 
sleep macro time
	mov al, 0
	mov ah, 86h
	mov cx, time
	int 15h
endm



;Макрос рисования окна
; xStart - левый верхний угол - столбец
; yStart - левый верхний угол - строка
; xEnd   - правый нижний угол - столбец
; yEnd   - правый нижний угол - строка
drawWindow macro xStart, yStart, xEnd, yEnd, color	
	mov ah, 06
	mov al, 00
	
	mov ch, yStart				;левый верхний угол - строка
	mov cl, xStart				;левый верхний угол - столбец
	
	mov dh, yEnd				;правый нижний угол - строка
	mov dl, xEnd				;правый нижний угол - столбец
	
	mov bh, color				;установка цвета фона и цвета букв
	
	int 10h						;прерывание отрисовки
endm




.386
c1 SEGMENT para public USE16 'code'
	ASSUME cs:c1, ds:d1, ss:st1

	
	
;Процедура прятания курсора
;устанавливает курсор за пределами окна	
hideCursor PROC	
	mov ah,2			;прячем курсор
	mov dh,26			;устанавливаем его за пределы экрана
	mov dl,81
	mov bh,0
	int 10h	
	ret
ENDP



;Процедура сдвига окна влево
leftShift PROC
	cmp mainWindowXStart, 0
	je retleftShift
	dec mainWindowXStart
	dec mainWindowXEnd
retleftShift:
	ret
ENDP



;Процедура сдвига окна вправо
rightShift PROC
	cmp mainWindowXEnd, 79
	je retrightShift
	inc mainWindowXStart
	inc mainWindowXEnd
retrightShift:
	ret
ENDP



;Процедура сдвига окна вверх
upShift PROC
	cmp mainWindowYStart, 0
	je relupShift
	dec mainWindowYStart
	dec mainWindowYEnd
relupShift:
	ret
ENDP



;Процедура сдвига окна вниз
downShift PROC
	cmp mainWindowYEnd, 23
	je downshitRet
	inc mainWindowYStart
	inc mainWindowYEnd
downshitRet:
	ret
ENDP



;Старт программы
start:	
	mov ax, videoSeg
	mov es, ax
	mov ax, d1
	mov ds, ax



;************************************** Вывод фразы: a long time ago in a galaxy far far away ***************************************
	drawWindow 0, 0, 80, 25, startBGColor	;на всю консоль черное окно, голубые буквы
	printInWindow LongTimeAgo, 11, 24		;вывод фразы в данном окне
	call hideCursor							;прячем курсор
	
	sleep 85								;ождиание
	
	mov ax, 03h								;очистка экрана
	int 10h		
	
	drawWindow 0, 0, 80, 25, titleBGColor	;на всю консоль черное окно, желтые буквы
	printInWindow Separator, 11, 34			;вывод		
	printInWindow Assembler, 12, 35
	printInWindow Separator, 13, 34
	call hideCursor							;прячем курсор
	
	sleep 75								;ождиание
	
	mov ax, 03h								;очистка экрана
	int 10h	
	
	
	
;************************************************* Циклическая отрисовка вступления *************************************************
;Циклическая отрисовка окна с текстом вступления
;окно двигается снизу вверх, кол-во строк меняется
writeLoop:
	drawWindow 20, countYStart, 60, countYEnd, titleBGColor
	
	cmp countYStart, 24d
	je print1IntroStr
	
	cmp countYStart, 23d
	je print2IntroStr
	
	cmp countYStart, 22d
	je print3IntroStr
	
	cmp countYStart, 21d
	je print4IntroStr
	
	cmp countYStart, 20d
	je print5IntroStr
	
	cmp countYStart, 19d
	je print6IntroStr
		
	printInWindow FullIntroduction, countYStart, 21
	
	cmp countYStart, 0
	je waitToStart
	
continueLoop:
	call hideCursor
	dec countYStart
	dec countYEnd	
	sleep 5
	
	loop writeLoop
	
print1IntroStr:
	printInWindow FullIntroduction1, countYStart, 21
	jmp continueLoop
	
print2IntroStr:
	printInWindow FullIntroduction2, countYStart, 21
	jmp continueLoop
	
print3IntroStr:
	printInWindow FullIntroduction3, countYStart, 21
	jmp continueLoop
	
print4IntroStr:
	printInWindow FullIntroduction4, countYStart, 21
	jmp continueLoop
	
print5IntroStr:
	printInWindow FullIntroduction5, countYStart, 21
	jmp continueLoop
	
print6IntroStr:
	printInWindow FullIntroduction6, countYStart, 21
	jmp continueLoop
	
	
	
;*********************************************** Переход от вступления к двиганию окна ***********************************************
;Ожидание нажатие F4/Enter
waitToStart:
	drawWindow 0, 24, 80, 24, waitBgColor
	printInWindow WaitToStartText, 24, 0
	call hideCursor
	mov ah, 000								;ожидание ввода клавиши
	int 16h
	cmp ax, 3E00h							;сравнение с ASCII-кодом клавиши F4
	je programEnd
	cmp ax, 1C0Dh							;сравнение с ASCII и скан кодом клавиши Enter
	je moveWindow
	loop waitToStart
	
	
	
;*********************************************************** Двигаем окно  ***********************************************************
moveWindow:
	mov ax, 03h		;очистка экрана
	int 10h			
	drawWindow mainWindowXStart, mainWindowYStart, mainWindowXEnd, mainWindowYEnd, mainBgColor
	drawWindow 0, 24, 80, 24, waitBgColor
	printInWindow WaitToMoveWiindow, 24, 0
	call hideCursor

	mov ah, 00								;ожидание ввода клавиши
	int 16h
	cmp ax, 3E00h							;сравнение с ASCII-кодом клавиши F4
	je programEnd
	
	cmp ax, 1C0Dh							;сравнение с ASCII и скан кодом клавиши Enter							
	je startInput
	
	cmp ax, 4B00h							;сравнение с ASCII и скан кодом клавиши 'Стрелочка влево'
	je moveLeft
	
	cmp ax, 4D00h							;сравнение с ASCII и скан кодом клавиши 'Стрелочка вправо'
	je moveRight
	
	cmp ax, 4800h							;сравнение с ASCII и скан кодом клавиши 'Стрелочка вниз'
	je moveUp
	
	cmp ax, 5000h							;сравнение с ASCII и скан кодом клавиши 'Стрелочка вверх'
	je moveDown
	
	loop moveWindow
	
moveLeft:
	call leftShift
	jmp moveWindow
	
moveRight:
	call rightShift
	jmp moveWindow
	
moveUp:
	call upShift
	jmp moveWindow
	
moveDown:
	call downShift
	jmp moveWindow
	
	
	
;********************************************************** Вводим значения **********************************************************
startInput:
	mov ax, 03h		;очистка экрана
	int 10h	
	drawWindow mainWindowXStart, mainWindowYStart, mainWindowXEnd, mainWindowYEnd, mainBgColor
	
	mov al, mainWindowXStart
	mov coursorX, al
	
	mov al, mainWindowYStart
	mov coursorY, al
	
	inc coursorX
	
	xor di,di		;di - номер числа в массиве
    mov cx, siz		;cx - размер массива
	
inputValues:
	push cx

	inc coursorY
	
	;Вывод сообщения о вводе строки
	printInWindow enter_please, coursorY, coursorX	
	input in_str 		;Ввод числа в виде строки
	
	call diapazon		;Проверка диапазона вводимых чисел (-29999,+29999)
	cmp bh, flag_err  	;Сравним bh и flag_err
	je inErr         	;Если равен 1 сообщение об ошибке ввода

	call dopust			;Проверка допустимости вводимых символов
	cmp bh, flag_err  	;Сравним bh и flag_err
	je inErr         	;Если равен 1 сообщение об ошибке ввода
	
	call AscToBin 	    ;Преобразование строки в число
	inc di
	inc di
	pop cx
	loop inputValues
	jmp searchMinMax
	
inErr:  
	drawWindow 27, 9, 53, 16, errorBgColor
	drawWindow 27, 15, 53, 16, errorBgColorWait
	printInWindow inputError, 12, 34
	printInWindow WaitAnyKeyToExit, 15, 30
	call hideCursor
	mov ah, 000								;ожидание ввода клавиши
	int 16h
	jmp programEnd
	
	
	
;***************************************** Нахождение миниамльного и максимального элементов *****************************************
searchMinMax:
	mov cx, siz				;cx - размер массива
	mov si, offset number
	xor ax, ax
	
searchLoop:					;для поиска минимальных и максимальных
	mov ax,[si]				;элементов была принята нулевая гипотеза:
							;max = -29999 (минимальный  из возможных элементов)
							;min =  29999 (максимальный из возможных элементов)
	
findMax:
	cmp ax, max				;если найден элемент больше текущего значения max,
	jg 	foundMax			;то переход к перезаписи max
	
findMin:
	cmp ax, min				;если найден элемент меньше текущего значения min,
	jl foundMin			    ;то переход к перезаписи min
	jmp nextVal				;иначе переход к следующему элементу
	
foundMax:
	mov max, ax				;перезапись max элемента
	jmp findMin

foundMin:
	mov min,ax				;перезапись min элемента
	
nextVal:
	inc si
	inc si
	loop searchLoop

	cmp max, 0
	je checkMinZero
	jmp searchPosSum
	
	
checkMinZero:
	cmp min, 0
	je resOutput
	jmp searchPosSum
	
	
;********************************************* Нахождение суммы положительных элементов **********************************************
searchPosSum:
	mov cx, siz				;cx - размер массива
	mov si, offset number
	xor ax, ax
sumPositive:
	mov ax,[si]
	cmp ax, 0				;сравнение с 0
	jl negative				;если число меньше то переход
	add sumPos,ax			;иначе сложение с переменной
	jo overFlowErr     		;если переполнение, то переход   
negative:		
	inc si
	inc si
	loop sumPositive

;****************************************** Нахождение произведения отрицательных элементов ******************************************
searchNegMul:
	mov cx, siz				;cx - размер массива
	mov si, offset number
	
	mov ax, 1				;для циклического умножения заносим 1
							;поскольку умножение всегда просиходит с регистром ax
	
minusEl:
	mov bx,[si]
	cmp bx, 00h
	jge plusEl				;если положительный элемент - идем дальше
	imul bx					;иначе умножаем
	jo overFlowErr 			;проверяем на переполнение
	
plusEl:
	inc si
	inc si
	loop minusEl
	
	mov mulNeg, ax			;заносим значение в переменную 



;****************************** Нахождение разницы суммы положительных и разницы отрицательных элементов *****************************
searchDiffSumMul:		;поскольку вычитание (sub) работает по принципу:
	mov ax, sumPos		;  <Приемник>=<Приемник>-<Источник>
	sub ax, mulNeg      ;чтобы не испортить результаты предудыщих вычислений
	mov diffRez, ax		;мы одну из перенных переносим в отдельный регистр, где и сохраним
						;результат, а затем запишем значение регистра в переменную
	
	
	
	
	jmp resOutput		;переход к выводу результатов 
	
	
	
;*********************************************************** Вывод ошибок ************************************************************
overFlowErr:		
	drawWindow 27, 9, 53, 16, errorBgColor
	drawWindow 27, 15, 53, 16, errorBgColorWait
	printInWindow overflow, 12, 36
	printInWindow WaitAnyKeyToExit, 15, 30
	call hideCursor
	mov ah, 00
	int 16h
	jmp programEnd
	
	
	
;*************************************************** Вывод полученных результатов ****************************************************
resOutput:
	drawWindow 20, 9, 65, 17, resultBGColor
	printInWindow Result, 10, 39
	printInWindow sumPosText, 12, 21
	mov ax, sumPos
	call BinToAsc
	print out_str

	mov cx,6			;очистка буфера вывода
	xor si,si
clear1:		
	mov [out_str+si],' '
	inc si
	loop clear1

	printInWindow mulNegText, 13, 21
	mov ax,mulNeg	
	call BinToAsc
	print out_str

	mov cx,6			;очистка буфера вывода
	xor si,si
clear2:		
	mov [out_str+si],' '
	inc si
	loop clear2

	printInWindow diffResText, 14, 21
	mov ax,diffRez	
	call BinToAsc
	print out_str
	
	mov cx,6			;очистка буфера вывода
	xor si,si
		
clear3:		
	mov [out_str+si],' '
	inc si
	loop clear3

	printInWindow maxElText, 15, 21
	mov ax,max	
	call BinToAsc
	print out_str
		
	mov cx,6			;очистка буфера вывода
	xor si,si
	
clear4:		
	mov [out_str+si],' '
	inc si
	loop clear4

	printInWindow minElText, 16, 21
	mov ax,min	
	call BinToAsc
	print out_str
		
	mov cx,6			;очистка буфера вывода
	xor si,si
	
	
	
;*********************************************************** Вывод титров ************************************************************
	drawWindow 0, 24, 80, 24, waitBgColor
	printInWindow WaitToReloadOrExit1, 24, 0
	
pressWait1:
	call hideCursor
	mov ah, 000								;ожидание ввода клавиши
	int 16h
	cmp ax, 3E00h							;сравнение с ASCII-кодом клавиши F4
	je programEnd
	cmp ax, 3F00h							;сравнение с ASCII-кодом клавиши F5
	je showCredits
	loop pressWait1
	
showCredits:
	drawWindow 0, 0, 80, 24, titleBGColor
	printInWindow credits, 8, 23
	drawWindow 0, 24, 80, 24, waitBgColor
	printInWindow WaitToExit, 24, 0
	call hideCursor

pressWait2:
	mov ah, 00								;ожидание ввода клавиши
	int 16h
	cmp ax, 3E00h							;сравнение с ASCII-кодом клавиши F4
	je programEnd
	loop pressWait2
	
	
	
programEnd:
	mov ax, 03h		;очистка экрана
	int 10h			
	mov ax, 4c00h	;завершение работы
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
end start