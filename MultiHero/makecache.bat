set ROOT=z:\build\mh
md %ROOT%\.gradle
mklink /D .gradle %ROOT%\.gradle
cd app
md %ROOT%\build
mklink /D %ROOT%\build
cd ..
