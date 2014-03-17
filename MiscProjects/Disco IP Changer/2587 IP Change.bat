@echo off
echo ==============================================================
echo                     Discobots FRC IP Setter
echo  Run this with Administrative Priviviges or it will not work.
echo ===============================================================
echo  After you click any key to continue, your IP will be set to:
echo  10.25.84.55 // 255.255.255.0 // 10.25.87.4
pause
echo Set IP of Local Area Connection & Netsh interface ip set address name="Local Area Connection" source=static 10.25.87.55 255.255.255.0 10.25.87.4
echo Set IP of Wireless Network Connection & Netsh interface ip set address name="Wireless Network Connection" source=static 10.25.87.55 255.255.255.0 10.25.87.4
pause
exit