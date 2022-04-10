@echo off
SETLOCAL ENABLEEXTENSIONS
SET me=%~n0
SET parent=%~dp0

SET output_folder=..\..\..\..\assets\anemulator\

REM Compile
echo compiling...
rgbasm -L -o "hello-world.o" "hello-world.asm"
rgbasm -L -o "sprite-test.o" "sprite-test.asm"
echo compilation finished

REM Link
echo linking...
rgblink -o "hello-world.gb" "hello-world.o"
rgblink -o "sprite-test.gb" "sprite-test.o"
echo linking finished

REM Rom Fix
echo fixing roms...
rgbfix -v -p 0xFF "hello-world.gb"
rgbfix -v -p 0xFF "sprite-test.gb"
echo fixing roms finished

Rem Cleanup
echo cleaning up
copy "hello-world.gb" "%output_folder%hello-world.gb"
copy "sprite-test.gb" "%output_folder%sprite-test.gb"
del "hello-world.o"
del "hello-world.gb"
del "sprite-test.o"
del "sprite-test.gb"
echo cleaning finished