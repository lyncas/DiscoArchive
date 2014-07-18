package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;
import org.discobots.aerialassist.HW;

public class Launcher extends Subsystem {

    private Solenoid launcherSolenoid1;
    private Solenoid launcherSolenoid2;
    private Solenoid launcherSolenoid3;

    public void initDefaultCommand() {
    }

    public Launcher() {
        launcherSolenoid1 = new Solenoid(HW.launcherSolenoidA);
        launcherSolenoid2 = new Solenoid(HW.launcherSolenoidB);
        launcherSolenoid3 = new Solenoid(HW.launcherSolenoidC);
    }

    public void fire(boolean shoot) {
        launcherSolenoid1.set(shoot);
        launcherSolenoid2.set(shoot);
        launcherSolenoid3.set(shoot);
    }

    public boolean isDown() {
        return !this.launcherSolenoid1.get();
    }
}
