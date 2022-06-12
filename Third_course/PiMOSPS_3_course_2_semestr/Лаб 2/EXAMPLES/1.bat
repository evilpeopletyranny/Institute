@echo off
:start
cls
echo=============================
echo 15s na vibor varianta
echo=============================
echo 1}Date
echo 2}Time
echo 3}Make File
echo 4}Read File
echo 5}Exit
echo=============================
choice /C:12345 /n /t:5,15
if errorlevel 5 goto exit
if errorlevel 4 goto vivod
if errorlevel 3 goto vvod
if errorlevel 2 goto tim
if errorlevel 1 goto dat
:dat
date
goto start
:tim
time
goto start
:vvod
if exist 1.txt goto new
echo Vvedite simvoly, zakonchit Ctrl+Z, Enter
echo==================================================
copy con 1.txt
pause
goto start
:new
echo File 1.txt uge sozdan. Ydalit? y/n
choice /n
if errorlevel 2 goto start
if errorlevel 1 goto del
:del 
del 1.txt
echo  Staryi 1.txt udalen! Sdelaem novyi!
goto vvod
:vivod
type 1.txt
pause
goto start
:exit
cls
