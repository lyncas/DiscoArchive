/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.discobots.aerialassist.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.aerialassist.HW;

/**
 *
 * @author Dylan
 */
public class CompressorSub extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Relay relay;
    Compressor comp;
    boolean enabled;
    public CompressorSub() {
        super("Compressor");
        //comp = new Compressor(1,HW.pressureSwitchAnalog, 1, HW.compressorRelay+1);
        relay = new Relay(1, HW.compressorRelay);
    }

    public void initDefaultCommand() {
    }

    public void on() {
        relay.set(Relay.Value.kOn);
        //comp.setRelayValue(Relay.Value.kOn);
        //comp.start();    
    }

    public void off() {
        relay.set(Relay.Value.kOff);
        //comp.setRelayValue(Relay.Value.kOff);
        //comp.stop();    
    }

    public boolean check() {
        return relay.get() == Relay.Value.kOn;
        //return comp.enabled();
    }
    
    public boolean getPressure()
    {
        return false;//comp.getPressureSwitchValue();
    }
}
