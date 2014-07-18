package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

public class Intake extends Subsystem {

    private Talon intakeMotor;
    private DoubleSolenoid intakeExtenderSolenoid;

    public void initDefaultCommand() {
        setExtended(true);
    }

    public Intake() {
        intakeMotor = new Talon(1, HW.intakeMotor);
        intakeExtenderSolenoid = new DoubleSolenoid(HW.intakeSolenoidA, HW.intakeSolenoidB);
    }

    public void setIntakeSpeed(double speed) {
        intakeMotor.set(speed);
    }

    public double getIntakeSpeed() {
        return intakeMotor.get();
    }

    public void setExtended(boolean on) {
        if (!on) {
            intakeExtenderSolenoid.set(DoubleSolenoid.Value.kForward);
        } else {
            intakeExtenderSolenoid.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public boolean isExtended() {
        return !(intakeExtenderSolenoid.get() == DoubleSolenoid.Value.kReverse);
    }
}