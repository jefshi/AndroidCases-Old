@echo off

set DATE_TIME=%date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%

git status
call :log "git status over"

git add . && git commit -m "push on %DATE_TIME%"
call :log "git commit successful"

git push -u origin master:master
call :log "git push successful"

call :presstoexit

exit

::============ Function ============

:log
echo. && echo %1 && echo.
exit /b

:presstoexit
set /p var="please press any key to exit."
:: mshta vbscript:msgbox(%1,64,%2)(window.close)
:: msg %2 /time:5 %1
exit /b
