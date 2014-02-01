package org.discobots.aerialassist.commands;

/**
 *
 * @author Nolan Shah
 */
public class ToggleCompressor extends CommandBase {

    public ToggleCompressor() {
        requires(compressorSub);
    }

    protected void initialize() {
        if (compressorSub.check()) {
            compressorSub.off();
        } else {
            compressorSub.on();
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
