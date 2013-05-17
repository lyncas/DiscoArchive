@echo off
echo ===============================================================
echo                       Discobots IP Setter                      
echo  Setting your IP. This program assumes your adapter is named
echo  "Local Area Connection". If it is named something else please
echo  open this file with Notepad and edit "Local Area Connection"
echo  to whatever your adapter is called. RUN THIS AS ADMIN!
echo ===============================================================
echo  After you click any key to continue, your IP will be set to:
echo  10.25.84.55 // 255.0.0.0 // 10.25.87.4
pause
@echo set ip & Netsh interface ip set address name="Local Area Connection" source=static 10.25.87.55 255.0.0.0 10.25.87.4
pause
exit

Note: Nolan's laptop's wifi adapter is called "Wi-Fi"
