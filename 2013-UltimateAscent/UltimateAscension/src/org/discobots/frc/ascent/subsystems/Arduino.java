package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;

/**
 *
 * @author Nolan Shah
 */
public class Arduino extends Subsystem {

    
    private I2C arduino;
    private DigitalModule sidecar;

    public Arduino() {
        sidecar = DigitalModule.getInstance(HW.digitalModuleSlot);
        arduino = sidecar.getI2C(HW.localI2CAddress);
    }

    protected void initDefaultCommand() {
    }

    public void writeByteToArduinoI2C(byte[] data) {
        byte[] buffer = new byte[data.length + 1];
        buffer[0] = HW.arduinoI2CAddress;
        System.arraycopy(data, 0, buffer, 1, data.length);
        arduino.transaction(buffer, buffer.length, null, 0);
    }
    public void writeLEDData(byte red, byte green, byte blue, byte attr) {
        this.writeByteToArduinoI2C(new byte[] {red, green, blue, attr});
    }
}
