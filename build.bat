@echo off
setlocal

REM Navegar a la primera carpeta y ejecutar mvnw clean package
cd /d "%~dp0auth"
call .\mvnw clean package
if %ERRORLEVEL% neq 0 (
    echo Error al crear el ejecutable del servicio auth. Abortando.
    pause
    exit /b %ERRORLEVEL%
)

REM Navegar a la primera carpeta y ejecutar mvnw clean package
cd /d "%~dp0springboot-servicio-config-server"
call .\mvnw clean package
if %ERRORLEVEL% neq 0 (
    echo Error al crear el ejecutable del servicio config-server. Abortando.
    pause
    exit /b %ERRORLEVEL%
)

REM Navegar a la primera carpeta y ejecutar mvnw clean package
cd /d "%~dp0springboot-servicio-eureka"
call .\mvnw clean package
if %ERRORLEVEL% neq 0 (
    echo Error al crear el ejecutable del servicio eureka server. Abortando.
    pause
    exit /b %ERRORLEVEL%
)

REM Navegar a la primera carpeta y ejecutar mvnw clean package
cd /d "%~dp0springboot-servicio-gateway"
call .\mvnw clean package
if %ERRORLEVEL% neq 0 (
    echo Error al crear el ejecutable del servicio gateway. Abortando.
    pause
    exit /b %ERRORLEVEL%
)

REM Navegar a la primera carpeta y ejecutar mvnw clean package
cd /d "%~dp0springboot-servicio-items"
call .\mvnw clean package
if %ERRORLEVEL% neq 0 (
    echo Error al crear el ejecutable del servicio items. Abortando.
    pause
    exit /b %ERRORLEVEL%
)

REM Navegar a la primera carpeta y ejecutar mvnw clean package
cd /d "%~dp0springboot-servicio-productos"
call .\mvnw clean package
if %ERRORLEVEL% neq 0 (
    echo Error al crear el ejecutable del servicio productos. Abortando.
    pause
    exit /b %ERRORLEVEL%
)

REM Navegar a la primera carpeta y ejecutar mvnw clean package
cd /d "%~dp0springboot-servicio-usuarios"
call .\mvnw clean package
if %ERRORLEVEL% neq 0 (
    echo Error al crear el ejecutable del servicio usuarios. Abortando.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo.
echo Operacion completada exitosamente

pause