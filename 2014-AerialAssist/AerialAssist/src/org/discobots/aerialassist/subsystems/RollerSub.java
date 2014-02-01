package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Nolan Shah
 */
public class RollerSub extends Subsystem {
    Victor rollerMotor;
    
    public void initDefaultCommand() {
    }
    
    public RollerSub() {
        rollerMotor = new Victor(1, HW.rollerMotor);
    }
    
    public void setSpeed(double speed) {
        rollerMotor.set(speed);
    }
}