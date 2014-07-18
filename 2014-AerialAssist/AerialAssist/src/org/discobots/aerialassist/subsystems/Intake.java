package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

public class RollerSub extends Subsystem {

    private Talon roller;
    private DoubleSolenoid extend;
    private DigitalInput armLimitSwitch;

    public void initDefaultCommand() {
        setExtended(true);
    }

    public RollerSub() {
        armLimitSwitch = new DigitalInput(1, HW.armLimitSwitchChannel);
        roller = new Talon(1, HW.rollerMotor);
        extend = new DoubleSolenoid(HW.extenderSolenoidA, HW.extenderSolenoidB);
    }

    public void setIntakeSpeed(double speed) {
        roller.set(speed);
    }

    public double getIntakeSpeed() {
        return roller.get();
    }

    public void setExtended(boolean on) {
        if (!on) {
            extend.set(DoubleSolenoid.Value.kForward);
        } else {
            extend.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public boolean isExtended() {
        return !(extend.get() == DoubleSolenoid.Value.kReverse);
    }

    public boolean getLimit() {
        return this.armLimitSwitch.get();
    }
}