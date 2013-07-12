
package org.discobots.frc.ascent.commands.compressor;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan
 */
public class CompressorOff extends CommandBase {
    
    public CompressorOff() {
    }

    protected void initialize() {
        compressorSubsystem.setCompressor(false);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
