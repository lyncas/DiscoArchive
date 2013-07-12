package org.discobots.frc.ascent.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.discobots.frc.ascent.HW;

/**
 *
 * @author nolan
 */
public class Compressor extends Subsystem {
    
    private edu.wpi.first.wpilibj.Compressor air;
    
    public Compressor() {
        air = new edu.wpi.first.wpilibj.Compressor(HW.pressureswitchSlot, HW.pressureswitchChannel, HW.compressorSlot, HW.compressorChannel);
    }

    public boolean isCompressorEnabled() {
        return air.enabled();
    }

    public void setCompressor(boolean val) {
        if (val) {
            air.start();
        } else {
            air.stop();
        }
    }
    
    public boolean getPressure() {
        return air.getPressureSwitchValue();
    }
    
    protected void initDefaultCommand() {

    }

}
