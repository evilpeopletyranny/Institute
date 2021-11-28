.model small
.stack 100h
.data
	bios  equ 0FFFFh
	video equ 0B800h
	color equ 10111010b
	
.code
_main:
	mov ah,0
	mov al,3
	int 10h
	mov ax,bios
	mov es,ax
	
	mov ax, video
	mov ds,ax
	
	mov si, 05h
	mov di, 00h
	mov cx, 0008h
	
_loop:
	mov al, es:[si]
	mov ah,color
	mov ds:[di], ax
	inc di
	inc di
	inc si
	loop _loop
	
	mov ah, 4ch
	mov al, 0
	int 21h
end _main