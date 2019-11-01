set BUILD_ROOT=%temp%\MULTIHERO
md %BUILD_ROOT%\build
md %BUILD_ROOT%\app\build
md %BUILD_ROOT%\test\build
mklink /J build %BUILD_ROOT%\build
mklink /J app\build %BUILD_ROOT%\app\build
mklink /J test\build %BUILD_ROOT%\test\build

