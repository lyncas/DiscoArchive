@echo off
echo ==============================================================
echo                    Discobots FRC IP Resetter
echo  Run this with Administrative Priviviges or it will not work.
echo ==============================================================
echo  If DHCP is already enabled on this interface then you don't 
echo  need to do anything. If you cannot connect to the internet, 
echo  then edit your adapter's static IP manually.
echo ==============================================================
echo  Press any key to reset this device's IP address.
pause
echo Reset IP on Local Area Connection & Netsh interface ip set address name="Local Area Connection" source=dhcp
echo Reset IP on Wireless Network Connection & Netsh interface ip set address name="Wireless Network Connection" source=dhcp
pause
exit