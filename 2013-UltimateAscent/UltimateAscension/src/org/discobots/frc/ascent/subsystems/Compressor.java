package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;

/**
 *
 * @author nolan
 */
public class Compressor extends Subsystem {
    
    private edu.wpi.first.wpilibj.Compressor air;
    private Relay airRelay;
    
    public Compressor() {
        //air = new edu.wpi.first.wpilibj.Compressor(HW.pressureswitchSlot, HW.pressureswitchChannel, HW.compressorSlot, HW.compressorChannel);
        airRelay = new Relay(HW.compressorSlot, HW.compressorChannel);
    }

    public boolean isCompressorEnabled() {
        //return air.enabled();
        return airRelay.get() == Relay.Value.kOn ? true : false;
    }

    public void setCompressor(boolean val) {
        if (val) {
            airRelay.set(Relay.Value.kOn);
            // air.start();
        } else {
            airRelay.set(Relay.Value.kOff);
            //air.stop();
        }
    }
    
    public boolean getPressure() {
        return air.getPressureSwitchValue();
    }
    
    protected void initDefaultCommand() {

    }

}
