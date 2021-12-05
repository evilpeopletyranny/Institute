p1 macro f1		; B뢮� ᮮ�饭�� �� �࠭
	push ax
	push dx
	mov dx, offset f1
	mov ah, 09h
	mov ah, 09h
	int 21h
	pop dx
	pop ax
endm


p2 macro f2 	; B��� ��ப� ᨬ�����
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
	
	in_str label byte 	; C�ப� ᨬ����� (�� ����� 6)
	razmer db 7
	kol db (?)
	stroka db 7 dup (?)
	
	
	number dw 5 dup (0)   ; M��ᨢ �ᥫ
	
	siz dw 5              ; K�����⢮ �ᥫ
	
	negNum dw 5 dup (0)
	posNum dw 5 dup (0)
	sum dw 5 dup(0)
	
	perevod db 10,13,'$'
	text_err1 db 'Input Error!', 10,10,'$'
	div_zero db 'Divition by zero!', 10,10,'$'
	messovf db 13,10,7,'Overflow!','$'
	MulNeg db 13,10,'Mul of negatives: ','$'
	messageMinChet db 13,10,'Min of even: ','$'
	star db '*  $'
	equal db '=  $'
	
	out_str db 6 dup (' '),'$'
	
	flag_err equ 1
	
d1 ENDS



c1 SEGMENT para public 'code'
ASSUME cs:c1, ds:d1, ss:st1

start:	
	mov ax, d1
	mov ds, ax
	
	;��⠭���� ⥪�⮢��� �����०���, ���⪠ �࠭�
	mov ax, 03h  	;ah=0 (����� �㭪樨),al=3 (����� ०���)
	int 10h

    xor di,di
    mov cx, siz 	; � cx - ࠧ��� ���ᨢ�
	
vvod:	
	push cx

m1:	
	p1 mess1    ; �뢮� ᮮ�饭�� � ����� ��ப�
	p2 in_str 	; ���� �᫠ � ���� ��ப�
	
	p1 perevod
	
	call diapazon	; �஢�ઠ ��������� �������� �ᥫ (-29999,+29999)
	
	cmp bh, flag_err  	; �ࠢ��� bh � flag_err
	je err1         	; �᫨ ࠢ�� -ᮮ�饭�� �� �訡�� �����
	

	call dopust		; �஢�ઠ �����⨬��� �������� ᨬ�����
	
	cmp bh, flag_err
	je err1
	
	call AscToBin 	; �८�ࠧ������ ��ப� � �᫮
	inc di
	inc di
	pop cx
	loop vvod
	jmp m2
	
err1:   
	p1 text_err1	
	jmp endprog
	
; ->>>>>>>>>>>>>>>>>>>>>>>>> ��䬥��᪠� ��ࠡ�⪠ <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<-----------------
m2:	
	mov cx, siz 	; � (cx) - ࠧ��� ���ᨢ�
	mov si, offset number
	mov di, offset negNum
	xor bx, bx
negFind:	
	mov ax, [si]
	cmp ax, 0
	jge endNegFind
	mov [di], ax
	inc di
	inc di
	inc bl
	
endNegFind:
	inc si
	inc si
	loop negFind
	
	mov cx, siz
	mov si, offset number
	mov di, offset posNum
	
posFind:	
	mov ax, [si]
	cmp ax, 0
	jl endPosFind
	mov [di], ax
	inc di
	inc di
	inc bh
	
endPosFind:
	inc si
	inc si
	loop posFind
	
	xor cx, cx
	
	cmp bl, bh
	jge bhLess
	mov cl, bl
	jmp blLess
bhLess:
	mov cl, bh
	
blLess:

	cmp cx, 00h
	jne _clearDI
	jmp endprog
	
	
	
_clearDI:
	xor di, di
	
mainLoop:
	mov ax, posNum[di]
	call BinToAsc
	p1 out_str
	call clearOutputStr
	
	p1 star
	
	mov ax, negNum[di]
	call BinToAsc
	p1 out_str
	call clearOutputStr

	p1 equal

	mov ax, posNum[di]
	mov bx, negNum[di]
	imul bx
	jo overflow

	add sum, ax
	
	call BinToAsc
	p1 out_str
	call clearOutputStr
	
	p1 perevod
	
	
	inc di
	inc di
	loop mainLoop
	jmp sumOut
	jmp endprog

sumOut:
	p1 perevod
	mov ax, sum
	call BinToAsc
	p1 out_str
	call clearOutputStr
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
;* �஢�ઠ ��������� �������� �ᥫ -29999,+29999	*
;* ��㬥���:										*
;* 		���� ����� - stroka						*
;* 													*
;* �������:										*
;* 		bh - 䫠� �訡�� �����						*
;****************************************************
	
DIAPAZON PROC
    xor bh, bh
	xor si, si
	
	cmp kol, 05h 	; �᫨ ����� ����� 5 ᨬ�����, �஢�ਬ �� �����⨬����
	jb dop
		
	cmp stroka, 2dh 	; E᫨ ����� 5 ��� ����� ᨬ����� �஢�ਬ ���� �� ���� ����ᮬ
	jne plus 	; E᫨ 1 ᨬ��� �� �����, �஢�ਬ �᫮ ᨬ�����
	
	cmp kol, 06h 	; E᫨ ���� - ����� � ᨬ����� ����� 6 �஢�ਬ �����⨬���� ᨬ����� 
	jb dop        
	
	inc si		; ���� �஢�ਬ ����� ����
	jmp first

plus:   
	cmp kol,6	; B������ 6 ᨬ����� � ���� - �� ����� 
	je error1	; O訡��
	
first:  
	cmp stroka[si], 32h	; Cࠢ��� ���� ᨬ��� � '2'
	jna dop		; E᫨ ���� <= '2' - �஢�ਬ �����⨬���� ᨬ�����
	
error1:
	mov bh, flag_err	; ���� bh = flag_err
	
dop:	
	ret
DIAPAZON ENDP


;****************************************************
;* �஢�ઠ �����⨬��� �������� ᨬ�����			*
;* ��㬥���:										*
;* 		���� ����� - stroka						*
;*		si - ����� ᨬ���� � ��ப�					*
;* 													*
;* �������:										*
;* 		bh - 䫠� �訡�� �����						*
;****************************************************
DOPUST PROC

	xor bh, bh
    xor si, si
	xor ah, ah
	xor ch, ch
	
	mov cl, kol	; � (cl) ������⢮ ��������� ᨬ�����
	
m11:	
	mov al, [stroka + si] 	; B (al) - ���� ᨬ���
	cmp al, 2dh	; ������� �� ᨬ��� ����ᮬ
	jne testdop	; �᫨ �� ����� - �஢�ઠ �����⨬���
	cmp si, 00h	; �᫨ �����  - ���� �� �� ���� ᨬ�����
	jne error2	; �᫨ ����� �� ���� - �訡��
	jmp m13
	
testdop:
	cmp al, 30h	;������� �� �������� ᨬ��� ��ன
	jb error2
	cmp al, 39h
	ja error2
	
m13: 	
	inc si
	loop m11
	jmp m14
	
error2:	
	mov bh, flag_err	; �� �������⨬��� ᨬ���� bh = flag_err
	
m14:	
	ret
DOPUST ENDP

;****************************************************
;* ASCII to number									*
;* ��㬥���:										*
;* 		B cx ������⢮ ��������� ᨬ�����			*
;*		B bx - ����� ᨬ���� ��稭�� � ��᫥����� 	*
;* 													*
;* �������:										*
;* 		���� �ᥫ - number						*
;*		B di - ����� �᫠ � ���ᨢ�				*
;****************************************************
AscToBin PROC
	xor ch, ch
	mov cl, kol
	xor bh, bh
	mov bl, cl
	dec bl
	mov si, 01h  ; � si ��� ࠧ�鸞
	
n1:	
	mov al, [stroka + bx]
	xor ah, ah
	cmp al, 2dh	; �஢�ਬ ���� �᫠
	je otr	; E᫨ �᫮ ����⥫쭮�
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
	neg [number + di]	; �।�⠢�� ����⥫쭮� �᫮ � �������⥫쭮� ����
	
n2:	
	ret
AscToBin ENDP

;****************************************************
;* Number to ASCII									*
;* ��㬥���:										*
;* 		��᫮ ��।����� �१ ax					*
;* 													*
;* �������:										*
;* 		���� �ᥫ - out_str						*
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

clearOutputStr PROC
	xor si, si
	mov si, offset out_str
	push cx
	mov cl, 06h
_clearCycle:
	mov [si], byte ptr ' '
	inc si
	loop _clearCycle
	pop cx
	ret
ENDP
      
	  
c1 ENDS		
st1 SEGMENT para stack 'stack'
	dw 10 dup (?)
st1 ENDS

end start