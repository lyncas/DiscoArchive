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
import edu.wpi.first.wpilibj.Victor;

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
    boolean asdf;
    Victor whyWillTheSpikeNotWork;
    public CompressorSub() {
        super("Compressor");
        comp = new Compressor(1,HW.pressureSwitch, 1, HW.compressorRelay);
        //relay = new Relay(1, HW.compressorRelay);
        //whyWillTheSpikeNotWork = new Victor(1, HW.spikeReplacementVictor);
    }

    public void initDefaultCommand() {
    }

    public void on() {
        //relay.set(Relay.Value.kForward);
        //comp.setRelayValue(Relay.Value.kOn);
        if (comp.getPressureSwitchValue()==true)
            comp.stop();
        else
            comp.start();    
        //whyWillTheSpikeNotWork.set(1);
    }

    public void off() {
        //relay.set(Relay.Value.kOff);
        //comp.setRelayValue(Relay.Value.kOff);
        comp.stop();    
        //whyWillTheSpikeNotWork.set(0);
    }

    public boolean check() {
        //return relay.get() != Relay.Value.kOff;
        return comp.enabled();
        //return 1 == whyWillTheSpikeNotWork.get();
    }
    
    public boolean getPressure()
    {
        return comp.getPressureSwitchValue();
    }
}
