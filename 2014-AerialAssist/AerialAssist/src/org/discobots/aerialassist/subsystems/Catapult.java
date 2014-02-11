package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Patrick
 */
public class Catapult extends Subsystem {

    private Victor catapultLauncher;
    private DigitalInput sensor;
    private boolean manualFire;

    public void initDefaultCommand() {
    }

    public void Catapult() {
        catapultLauncher = new Victor(HW.catapultMotor);
        sensor = new DigitalInput(1, HW.chooChooTouchSensor);
    }

    public void run() {
        catapultLauncher.set(1);
    }

    public void stop() {
        catapultLauncher.set(0);
    }

    public void toggleManualFire() {
        manualFire = !manualFire;
    }

    public boolean getManual() {
        return manualFire;
    }
    
    public boolean getTouchValue() {
        return sensor.get();
    }
    
}
