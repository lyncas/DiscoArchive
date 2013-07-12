package org.discobots.frc.ascent.commands.compressor;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan
 */
public class CompressorOn extends CommandBase {
    
    public CompressorOn() {
    }

    protected void initialize() {
        compressorSubsystem.setCompressor(true);
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
