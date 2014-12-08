@echo off
set /p _type="input JAVA/OBJC/JS:"
if "%_type%" == "JAVA" java -Djava.ext.dirs=lib com.sfebiz.codegenTool.Main JAVA
if "%_type%" == "OBJC" java -Djava.ext.dirs=lib com.sfebiz.codegenTool.Main OBJC
if "%_type%" == "JS" java -Djava.ext.dirs=lib com.sfebiz.codegenTool.Main JS
pause
