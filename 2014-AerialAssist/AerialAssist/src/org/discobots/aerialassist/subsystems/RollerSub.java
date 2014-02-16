package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Nolan Shah
 */
public class RollerSub extends Subsystem {

    private Victor roller;
    private Solenoid extend;
    
    public void initDefaultCommand() {
    }
    
    public RollerSub() {
        roller = new Victor(1, HW.rollerMotor);
        extend = new Solenoid(HW.extenderSolenoid);
    }
    
    public void setIntakeSpeed(double speed) {
        roller.set(speed);
    }
    
    public double getIntakeSpeed() {
        return roller.get();
    }
    
    public void setExtended(boolean on) {
        extend.set(on);
    }
    
    public boolean isExtended() {
        return extend.get();
    }
}