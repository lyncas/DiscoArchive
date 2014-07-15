package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SensorBase;

/**
 * UltrasonicSRF02_I2C 
 * Class for Ultrasonic Sensor SRF02 in I2C Mode.
 * http://www.robot-electronics.co.uk/htm/srf02techI2C.htm
 * 
 * @author Nolan Shah
 */
public class UltrasonicSRF02_I2C extends SensorBase {
    private I2C i2cBus;
    private int[] sampleData = new int[50]; // <------------------- Sample Size
    private int pointer = 0;
    private UltrasonicUpdateThread updateThread;
    int address;
    public UltrasonicSRF02_I2C(int address) {
        UltrasonicSRF02_I2C.setDeviceAddress(242);
        i2cBus = DigitalModule.getInstance(getDefaultDigitalModule()).getI2C(address);
            System.out.println("[I2C SRF02] Created i2c bus object at address " + address);
        if (!i2cBus.addressOnly()) {
            System.out.println("[I2C SRF02] Test for device successful, address " + address);
        } else {
            System.err.println("[I2C SRF02] Test for device FAILED, address " + address);
            System.out.println("[I2C SRF02] Continuing...");
        }
        updateThread = new UltrasonicUpdateThread();
        updateThread.start();
    }
    
    private class UltrasonicUpdateThread extends Thread {
        public void run() {
            while (true) {
                i2cBus.write(0, 80); // sets unit to centimeters and sends request for data (80 for in, 81 for cm, 82 for ms)
                byte[] buffer = new byte[2];
                i2cBus.read(2, 2, buffer);
                int latestData = (buffer[0] << 8) + buffer[1];
                if (latestData > 0) {
                    sampleData[pointer] = latestData;
                    pointer = (pointer == (sampleData.length - 1)) ? 0 : pointer + 1;
                    System.out.println(buffer[0] + " " + buffer[1]);
                }
                try {
                    Thread.sleep(15); // <------------------- Update Wait Time
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public int getLatestRawValue() {
        return sampleData[pointer - 1];
    }
    
    public int getAverageValue() {
        int sum = 0; // it pains me to sum it this way, but simpler is better.
        for (int i = 0; i < sampleData.length; i++) {
            sum += sampleData[i];
        }
        return sum / sampleData.length;
    }
    
    public static void setDeviceAddress(int newAddress) {
        setDeviceAddress(224, newAddress); // 224 is the default shipped address
    }
    public static void setDeviceAddress(int oldAddress, int newAddress) {
        System.out.println("Sending SRF02 at " + oldAddress + " its new address: " + newAddress);
        int[] a = {0xA0, 0xAA, 0xA5, newAddress};
        System.out.println("[I2C SRF02] Writing new address (" + newAddress + ") to device at old address " + oldAddress);
        for (int i = 0; i < 4; i++) {
            DigitalModule.getInstance(getDefaultDigitalModule()).getI2C(oldAddress).write(0, a[i]);
        }
    }
}
