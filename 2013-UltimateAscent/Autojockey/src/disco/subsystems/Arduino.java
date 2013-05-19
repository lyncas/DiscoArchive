package disco.subsystems;

import disco.HW;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arduino extends Subsystem{
    
    DigitalOutput arduino;
    boolean firstrun = true;
    public static int red, green, blue; // TODO: Write get/set methods. Do not directly use this in the updateLEDs Methods.
    
    protected void initDefaultCommand() {
    }
    
    public Arduino() {
        red = 0;
        green = 0;
        blue = 0;
        arduino = new DigitalOutput(HW.arduinoSlot, HW.arduinoChannel);
    }
    
    public void handshake() {
       arduino.pulse(1.00);
       arduino.pulse(0.10);
    }
    
    public void updateLEDS(int type) {
        handshake();
        arduino.pulse(type / 127);
        arduino.pulse(0);
        arduino.pulse(0);
        arduino.pulse(0);
    }
    public void updateLEDS(int type, int r, int g, int b) {
        handshake();
        arduino.pulse(type / 127);
        arduino.pulse(r / 255);
        arduino.pulse(g / 255);
        arduino.pulse(b / 255);
    }
}