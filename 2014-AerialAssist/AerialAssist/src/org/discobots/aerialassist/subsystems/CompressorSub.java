/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;
import org.discobots.aerialassist.utils.BetterCompressor;

/**
 *
 * @author Dylan
 */
public class CompressorSub extends Subsystem {
    BetterCompressor comp;

    public CompressorSub() {
        super("Compressor");
        comp = new BetterCompressor(1, HW.pressureSwitch, 1, HW.compressorRelay, 1, HW.spikeReplacementVictor);
    }

    public void initDefaultCommand() {
    }

    public void on() {
        comp.start();
    }

    public void off() {
        comp.stop();
    }

    public boolean check() {
        //return relay.get() != Relay.Value.kOff;
        return comp.enabled();
    }

    public boolean getPressure() {
        return comp.getPressureSwitchValue();
    }
}
