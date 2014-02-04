package org.discobots.aerialassist.subsystems;

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
    Solenoid extenderSolenoid;
    
    public void initDefaultCommand() {
    }
    
    public RollerSub() {
        rollerMotor = new Victor(1, HW.rollerMotor);
        extenderSolenoid = new Solenoid(1, HW.extenderSolenoid);
    }
    
    public void setSpeed(double speed) {
        rollerMotor.set(speed);
    }
    
    public void setExtender(boolean set) {
        extenderSolenoid.set(set);
    }
    
    public boolean getExtender() {
        return extenderSolenoid.get();
    }
    
}