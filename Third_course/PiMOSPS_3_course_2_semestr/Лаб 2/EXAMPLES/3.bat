@echo off
if not %1==c if not %1==a if not %1==l if not %1==all goto exit
if %1==all goto m1
if %1==c bcc -c -v -Ic:\borlandc\include %2 >NUL
if %1==a tasm -la -zi  %3 >NUL
if %1==l tlink -v c:\borlandc\lib\c0s.obj+%2+%3,1.exe,1.map,c:\borlandc\lib\cs.lib >NUL
goto end
:m1
bcc -c -v -Ic:\borlandc\include %2 >NUL
tasm -la -zi  %3 >NUL
tlink -v c:\borlandc\lib\c0s.obj+%2+%3,1.exe,1.map,c:\borlandc\lib\cs.lib >NUL
if errorlevel 1 echo LINK ERROR
if errorlevel 1 goto end
1.exe 
goto end
:exit
echo Bad command
:end