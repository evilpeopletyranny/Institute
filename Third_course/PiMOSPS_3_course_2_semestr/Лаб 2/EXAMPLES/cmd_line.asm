;�������� ����� � ��������� ������, ���� ��� ����� - �������������, ��� ����� ���� 
;���� /r ��� /g "��������" ����� ������� ��� �������
; ���������� 3 ���������, 25 �����*18 �������� 
;�� ����� 0-24 (0-18h) ; �� �������� 0-79 (0-4fh)
.model tiny
rect macro
	mov ah,6
	mov al,0
	mov cx,0
	mov dx,184fh	 ;  (0-24) (0-79)
	int 10h	
endm
.code
org 80h		;���. ������ �� �������� 80h �� ������ PSP
cmd_len db ?	;����� ���. ������ 
cmd_line db ?	;���������� ���. ������ 
org 100h
start:	mov ax,3			 ; ���������� 25*80
	int 10h
	cld			; ��� ��������� ������� = ����������� di
	mov cl,cmd_len		; ������� ��� ��������� �������=����� ��������� ������
	xor ch,ch
	mov di,offset cmd_line	; �������� ��������� ������ � PSP
	mov al,' '
	repne scasb		;��������� ��������� ������ �� 1-�� �������
	cmp byte ptr [di],'/'		;��������� ���� ���  / ?
	jne err2			;���, ������� ��� ������ ��������� � ������������� �����
	inc di			;��, �������, ��� ������
	cmp byte ptr [di], 'r'		;��������� ������� ����� /r
	je red			;��, ������ ���� /r - �� ���������
	cmp byte ptr [di],'g'		;��������� ������� ����� /g
	jne err1			;�� ��� ���� - ��������� �� ������
	mov bh,20h		;�������=�������
	rect
	jmp pr_end
red:	mov bh,40h		;�������=�������
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
