package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import org.discobots.frc.ascent.HW;

/**
 *
 * @author Nolan Shah
 */
public class Hanger extends Subsystem {
    
    Solenoid left, right;
    
    public Hanger() {
        left = new Solenoid(HW.solenoidHangerLeftChannel);
        right = new Solenoid(HW.solenoidHangerRightChannel);
    }
    
    public void setHangerPosition(boolean pos) {
        left.set(pos);
        right.set(pos);
    }
    
    public boolean getHangerPosition() {
        return left.get();
    }
    
    protected void initDefaultCommand() {
        
    }

}
