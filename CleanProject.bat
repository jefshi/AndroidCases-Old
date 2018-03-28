@echo off
cls

set ProjectDir=.

echo.
echo 删除工作目录下所有【Build】目录
echo.

rd /s /q %ProjectDir%\build\
rd /s /q %ProjectDir%\app\build\
rd /s /q %ProjectDir%\appnote\build\
rd /s /q %ProjectDir%\library\build\

echo.
echo 清空项目缓存成功
