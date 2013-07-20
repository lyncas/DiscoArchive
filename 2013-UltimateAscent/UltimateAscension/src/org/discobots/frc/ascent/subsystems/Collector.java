package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;

/**
 *
 * @author Nolan Shah
 */
public class Collector extends Subsystem {

    DoubleSolenoid nom;
    
    public Collector() {
        nom = new DoubleSolenoid(HW.solenoidCollectAChannel, HW.solenoidCollectBChannel);
    }
    
    protected void initDefaultCommand() {
        
    }
    
    public void setCollectorPosition(boolean a) {
        nom.set(a ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }
    
    public boolean getCollectorPosition() {
        return nom.get() == DoubleSolenoid.Value.kForward;
    }
}
