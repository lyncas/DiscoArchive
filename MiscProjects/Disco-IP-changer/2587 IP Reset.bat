@echo off
echo ===============================================================
echo                      Discobots IP Resetter                     
echo  Resetting your IP. This program assumes your adapter is named
echo  "Local Area Connection". If it is named something else please
echo  open this file with Notepad and edit "Local Area Connection"
echo  to whatever your adapter is called. RUN THIS AS ADMIN!
echo ===============================================================
echo  If DHCP is already enabled on this interface then you don't 
echo  need to do anything. If you cannot connect to the internet, 
echo  then edit your adapter's static IP manually.
echo ===============================================================
echo  After you click any key to continue, your IP will be reset.
pause
echo set ip & Netsh interface ip set address name="Local Area Connection" source=dhcp
pause
exit

Note: Nolan's laptop's wifi adapter is called "Wi-Fi"