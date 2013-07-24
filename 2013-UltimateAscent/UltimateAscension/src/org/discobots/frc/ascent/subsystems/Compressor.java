package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;
import org.discobots.frc.ascent.framework.MotorCompressor;

/**
 *
 * @author nolan
 */
public class Compressor extends Subsystem {
    
    private MotorCompressor air;
    private Talon motor;
    //private Relay airRelay;
    
    public Compressor() {
        motor=new Talon(HW.compressorTalonSlot,HW.compressorTalonChannel);
        air = new MotorCompressor(HW.pressureswitchSlot, HW.pressureswitchChannel, motor);
        //airRelay = new Relay(HW.compressorSlot, HW.compressorChannel);
    }

    public boolean isCompressorEnabled() {
        return air.enabled();
        //return airRelay.get() == Relay.Value.kOn ? true : false;
    }

    public void setCompressor(boolean val) {
        if (val) {
            //airRelay.set(Relay.Value.kOn);
             air.start();
        } else {
            //airRelay.set(Relay.Value.kOff);
            air.stop();
        }
    }
    
    public boolean getPressure() {
        return air.getPressureSwitchValue();
    }
    
    protected void initDefaultCommand() {

    }

}
