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
    private Solenoid extendA;
    private Solenoid extendB;
    
    public void initDefaultCommand() {
    }
    
    public RollerSub() {
        roller = new Talon(1, HW.rollerMotor);
        extendA = new Solenoid(HW.extenderSolenoidA);
        extendB = new Solenoid(HW.extenderSolenoidB);
    }
    
    public void setIntakeSpeed(double speed) {
        roller.set(speed);
    }
    
    public double getIntakeSpeed() {
        return roller.get();
    }
    
    public void setExtended(boolean on) {
        extendA.set(on);
        extendB.set(on);
    }
    
    public boolean isExtended() {
        return !extendA.get() && !extendB.get();   //I reversed it because the arm now defaults to down.
    }
}