package org.discobots.frc.ascent.commands.compressor;

import org.discobots.frc.ascent.commands.CommandBase;

/**
 *
 * @author Nolan
 */
public class CompressorToggle extends CommandBase {
    
    public CompressorToggle() {
    }

    protected void initialize() {
        compressorSubsystem.setCompressor(!compressorSubsystem.isCompressorEnabled());
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }
}
