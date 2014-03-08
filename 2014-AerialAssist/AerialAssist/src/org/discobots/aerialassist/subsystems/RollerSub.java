package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Nolan Shah
 */
public class RollerSub extends Subsystem {

    private Talon roller;
    private DoubleSolenoid extend;
    
    public void initDefaultCommand() {
    }
    
    public RollerSub() {
        roller = new Talon(1, HW.rollerMotor);
        extend = new DoubleSolenoid(HW.extenderSolenoidA,HW.extenderSolenoidB);
    }
    
    public void setIntakeSpeed(double speed) {
        roller.set(speed);
    }
    
    public double getIntakeSpeed() {
        return roller.get();
    }
    
    public void setExtended(boolean on) {
        if(!on)
            extend.set(DoubleSolenoid.Value.kForward);
        else
            extend.set(DoubleSolenoid.Value.kReverse);
    }
    
    public boolean isExtended() {
        return !(extend.get() == DoubleSolenoid.Value.kReverse);
    }
}