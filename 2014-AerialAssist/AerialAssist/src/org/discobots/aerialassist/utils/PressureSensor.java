package org.discobots.aerialassist.utils;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.SensorBase;

public class PressureSensor extends SensorBase {
    AnalogChannel analogChannel;
    public PressureSensor(int channelNumber) {
        analogChannel = new AnalogChannel(getDefaultAnalogModule(), channelNumber);
    }
    /**
     * Returns the PSI read from the Sensor
     * 
     * @return Pressure in PSI
     */
    public int getInteger() {
        double voltage = analogChannel.getAverageVoltage();
        double voltage0to4 = (voltage - 0.5);
        voltage0to4 = (voltage0to4 < 0) ? 0 : voltage0to4;
        return (int) ((voltage0to4) * 40.1785714286);
    }
    
    public double getDouble() {
        double voltage = analogChannel.getAverageVoltage();
        double voltage0to4 = (voltage - 0.5);
        voltage0to4 = (voltage0to4 < 0) ? 0 : voltage0to4;
        return (voltage0to4) * 40.1785714286;
    }
    
    public double getVoltage() {
        return analogChannel.getAverageVoltage();
    }
    
}
