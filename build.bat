@echo off
set "LIB_DIR=lib"
set "BIN_DIR=bin"
set "SRC_LIST=sources.txt"

if not exist %BIN_DIR% mkdir %BIN_DIR%

echo Compiling Java source files...
javac -d %BIN_DIR% -cp "%LIB_DIR%\*" @%SRC_LIST%

if %errorlevel% neq 0 (
    echo Compilation failed.
    exit /b %errorlevel%
)

echo Compilation successful.
