package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Nolan Shah
 */
public class ElToro extends Subsystem {
    Jaguar leftMotor, rightMotor;
    
    public void initDefaultCommand() {
    }
    
    public ElToro() {
        leftMotor = new Jaguar(1, HW.elToroLeft);
        rightMotor = new Jaguar(1, HW.elToroRight);
    }
    
    public void setSpeed(double speed) {
        leftMotor.set(speed);
        rightMotor.set(-speed);
    }
}