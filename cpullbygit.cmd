@echo off

git pull
call :log "git pull successful"

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
