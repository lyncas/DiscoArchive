package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.utils.BetterCompressor;

public class CompressorSub extends Subsystem {

    BetterCompressor comp;
    public boolean canRun;

    public CompressorSub() {
        super("Compressor");
        canRun = false;
        comp = new BetterCompressor(1, HW.pressureSwitch, 1, HW.compressorRelay, 1, HW.spikeReplacementVictor);
    }

    public void initDefaultCommand() {
        setRunPneumatics(true);
    }

    public void setRunPneumatics(boolean a) {
        canRun = a;
        SmartDashboard.putBoolean("Is compressor enabled?", canRun);
    }

    public boolean canRunPneumatics() {
        return canRun;
    }

    public void on() {
        comp.start();
    }

    public void off() {
        comp.stop();
    }

    public boolean isEnabled() {
        return comp.enabled();
    }

    public boolean isFull() {
        return comp.getPressureSwitchValue();
    }
}
