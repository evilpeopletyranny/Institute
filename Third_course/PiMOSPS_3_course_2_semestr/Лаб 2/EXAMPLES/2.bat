@echo off
help>> %cd%\123.txt
if /%1/==// goto error1
if not exist %1 goto error2
type %1
goto end
:error1 no arg
echo Need param - File name!
goto end
:error2 no file
echo File not exist
:end
