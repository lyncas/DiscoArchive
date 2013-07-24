/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;

/**
 *
 * @author Sam
 */
public class Collector extends Subsystem {

    private DoubleSolenoid armSolenoid;

    public Collector() {
        armSolenoid = new DoubleSolenoid(HW.solenoidArmDownChannel, HW.solenoidArmUpChannel);
        setArmPosition(true);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public boolean getArmPosition() {
        return armSolenoid.get() == DoubleSolenoid.Value.kForward;//down
    }

    public void setArmPosition(boolean down) {
        if (down) {
            armSolenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            armSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }
}
