package org.discobots.aerialassist.commands;

/**
 *
 * @author Nolan Shah
 */
public class ToggleCompressor extends CommandBase {

    public ToggleCompressor() {
        requires(compressor);
    }

    protected void initialize() {
        if (compressor.check()) {
            compressor.off();
        } else {
            compressor.on();
        }
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
