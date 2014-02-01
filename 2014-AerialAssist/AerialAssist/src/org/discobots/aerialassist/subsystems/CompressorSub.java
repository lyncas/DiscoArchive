/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Dylan
 */
public class CompressorSub extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Compressor comp;

    public CompressorSub() {
        super("Compressor");
        comp = new Compressor(1, HW.pressureSwitchAnalog, 1, HW.compressorDigital);
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
        return comp.enabled();
    }
}
