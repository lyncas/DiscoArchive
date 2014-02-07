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
    Victor rollerMotor;
    DoubleSolenoid extenderSolenoid;
    
    public void initDefaultCommand() {
    }
    
    public RollerSub() {
        rollerMotor = new Victor(1, HW.rollerMotor);
        extenderSolenoid = new DoubleSolenoid(1, HW.extenderASolenoid, HW.extenderBSolenoid);
    }
    
    public void setSpeed(double speed) {
        rollerMotor.set(speed);
    }
    
    public void setExtender(boolean set) {
        extenderSolenoid.set(set ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
     }
    
    public boolean getExtender() {
        if(extenderSolenoid.get() == DoubleSolenoid.Value.kForward)
            return true;
        else
            return false;
    }
    
}